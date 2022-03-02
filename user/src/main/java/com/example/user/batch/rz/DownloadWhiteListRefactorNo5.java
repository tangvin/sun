package com.example.user.batch.rz;

import com.alibaba.fastjson.JSON;
import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.MyTextFileWriter;
import com.example.user.bean.EiopOmgCustomerGroup;
import com.example.user.dao.EiopOmgCustomerGroupMapper;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author TY
 * @description 客群白名单重构方案5 ：已知文件的格式
 *    测试数据： 1536万 行数据  执行下时间： 15 分钟  ，生成的bin文件大小 1G 左右
 * @date 2021/9/30 9:45
 */
@Component
public class DownloadWhiteListRefactorNo5 extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(DownloadEiopOmgCustomerWhiteListSetNoFile.class);

    private int failCount;
    private int successCount;
    private boolean sendFlag;
    private int custCount;
    public static final String CG_STATUS_FALSE = "0";
    public static final String CG_STATUS_TWO = "1";
    public static final String CG_NUMBER = "CG_NUMBER";
    public static final String VERSION = "VERSION";
    public static final String CG_CREATE_WAY = "2";
    public static final String SAVE_CONTENT = "1";

    public static final String CG_FILE_TYPE_1 = "1";
    public static final String CG_FILE_TYPE_2 = "2";
    public static final String CG_FILE_TYPE_3 = "3";
    public static final String CG_FILE_TYPE_4 = "4";
    public static final String CG_FILE_TYPE_5 = "5";

    public static final String FILE_TYPE_TXT = "txt";
    public static final String FILE_TYPE_CSV = "csv";
    public static final String FILE_TYPE_ZIP = "zip";

    /**
     * 校验数字
     **/
    public static final String NUMBER_REGEX = "^[0-9]*$";
    /**
     * 校验数字和字母
     **/
    public static final String NUMBER_LETTER_REGEX = "^[A-Za-z0-9]+$";


    private MyTextFileWriter writer = null;
    @Autowired
    EiopOmgCustomerGroupMapper eiopOmgCustomerGroupMapper;

    @Override
    public void mainProcess() {
        System.out.println("开始时间:"+System.currentTimeMillis());
        super.mainProcess();
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile----->start");
        process();
        printLog();
        System.out.println("结束时间:"+System.currentTimeMillis());
    }

    public void printLog() {
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile successCount----->:{}", successCount);
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile failCount----->:{}", failCount);
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile----->end");
    }


    public void process() {
        String batchDateStr = "";
        String path = "D:\\testBIN";
//        String localPath1 = "D:\\path111\\111.txt";
        String localPath2 = "D:\\path222";
        boolean isOk = false;
        String BinName = "Test1110";
        //定义临时路径
        String binFileName = BinName + batchDateStr.replaceAll("-", "") + ".BIN";
        Path tempPath = null;
        try {
            //判断临时文件是否存在
            if (!judgeFile(path, binFileName)) {
                sendFlag = true;
                return;
            }
            //查询符合条件的数据
            List<Map<String, Object>> maps = eiopOmgCustomerGroupMapper.selectCustGroupInfo(CG_STATUS_FALSE, CG_CREATE_WAY, SAVE_CONTENT);
            if (!CollectionUtils.isEmpty(maps)) {
                for (Map<String, Object> map : maps) {
                    ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>(map);
                    UUID uuid = UUID.randomUUID();
                    try {
                        //通过oaas获取文件流
                        String localPath = "D:\\testBIN\\111.zip";
                        File localFile = new File(localPath);
                        FileInputStream fileInputStream = new FileInputStream(localFile);
                        Path unzipFilePath = null;
                        //文件内容
                        String cgfileType = (String) map.get("cg_file_type");
                        //文件格式
                        String fileType = "zip";
                        //获取本地文件
                        if (FILE_TYPE_TXT.equals(fileType)) {
                            tempPath = Paths.get(localPath2, File.separator + uuid.toString() + ".txt");
                            Files.copy(fileInputStream, tempPath);
                            isOk = validateByStream(cgfileType, tempPath);
                        } else if (FILE_TYPE_ZIP.equals(fileType)) {
                            tempPath = Paths.get(localPath2, File.separator + uuid.toString() + ".zip");
                            logger.info("====tempPath======>{}",tempPath);
                            Files.copy(fileInputStream, tempPath);
                            String destinationPath = tempPath.toFile().getPath().replace(".zip", "");
                            logger.info("====destinationPath======>{}",destinationPath);
                            String entryName = unZip(tempPath);
                            unzipFilePath = Paths.get(destinationPath + File.separator + entryName);
                            logger.info("====unzipFilePath======>{}",unzipFilePath);
                            isOk = readFiles(cgfileType, destinationPath);
                        }
                        if (isOk) {
                            saveDataToBin(tempPath, map, concurrentHashMap, unzipFilePath, fileType);
                        } else {
                            //更新状态为  5 -校验失败
                            int update = eiopOmgCustomerGroupMapper.updateStatusValidateFail((Long) map.get("id"));
                            logger.info("更新状态为  5 -校验失败update:{}",update);
                        }
                    } catch (Exception e) {
                        sendFlag = true;
                        //还原状态
                        logger.error("e:====", e);
                        e.printStackTrace();
                    }finally {
//                        deleteFinalTempPath(tempPath);
                    }
                }
            }
            writer.close();
            //将生成的bin文件发送到FTP
        } catch (Exception e) {
            logger.error("循环出现异常:{}", e);
            e.printStackTrace();
        } finally {
            if (sendFlag) {
                logger.error("出现异常了！");
            }
        }
    }

    /**
     * 删除临时目录
     * @param tempPath
     */
    private void deleteFinalTempPath(Path tempPath){
        try{
           if(tempPath.toFile().exists()){
               Files.delete(tempPath);
           }
        }catch (FileSystemException fse){
            logger.error("删除文件错误e:",fse);
        }catch (Exception e){
            logger.error("删除文件错误e:",e);
        }
    }

    /**
     * 保存数据到bin文件
     * @param tempPath
     * @param map
     * @param concurrentHashMap
     * @param unzipFilePath
     * @param fileType
     * @throws IOException
     */
    private void saveDataToBin(Path tempPath, Map<String, Object> map, ConcurrentHashMap<Object, Object> concurrentHashMap, Path unzipFilePath, String fileType) throws IOException {
        //写入bin文件
        //如果任务调度正常结束，修改客群关系为已经上传
        List<Map<String, Object>> cgStatusList = new ArrayList<>(1);
        Map<String, Object> maptree = new HashMap<>(2);
        maptree.put(CG_NUMBER, map.get(CG_STATUS_TWO));
        maptree.put(VERSION, map.get(VERSION));
        cgStatusList.add(maptree);
        //注入serives 操作
        int update = eiopOmgCustomerGroupMapper.updateOptimismLock((Long) map.get("id"), "1", "1.0");
        if (update > 0) {
            //保存客群数量
            HashMap<String, Object> hashMap = new HashMap<>(3);
            hashMap.put("id", (Long) map.get("id"));
            hashMap.put("custCount", custCount);
            logger.info("hashMap==={}", hashMap);
            int amount = eiopOmgCustomerGroupMapper.saveCustomerAmount(hashMap);
            logger.info("amount===:{}", amount);
            if (fileType.contains(FILE_TYPE_ZIP)) {
                Stream<String> stream = Files.lines(unzipFilePath);
                try {
                    stream.forEach(line -> {
                        concurrentHashMap.put("userId", line);
                        EiopOmgCustomerGroup eiopOmgCustomerGroup = JSON.parseObject(toJSONString(concurrentHashMap), EiopOmgCustomerGroup.class);
                        formatData(eiopOmgCustomerGroup);
                    });
                } finally {
                    stream.close();
                }
            } else if (fileType.contains(FILE_TYPE_TXT) || fileType.contains(FILE_TYPE_CSV)) {
                Stream<String> stream = Files.lines(tempPath);
                try {
                    stream.forEach(line -> {
                        concurrentHashMap.put("userId", line);
                        EiopOmgCustomerGroup eiopOmgCustomerGroup = JSON.parseObject(toJSONString(concurrentHashMap), EiopOmgCustomerGroup.class);
                        formatData(eiopOmgCustomerGroup);
                    });
                } finally {
                    stream.close();
                }
            }
        }
    }

    /**
     * 格式化数据写入bin文件
     *
     * @param bean
     */
    public void formatData(EiopOmgCustomerGroup bean) {
        StringBuilder sb = new StringBuilder("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        convertCgInvalidaDate(bean.getCgInvalidDate(),bean.getCreateTime());
        char delimiter = 27;
        sb.append(bean.getId()).append(delimiter)
                .append(bean.getCgName()).append(delimiter)
                .append(bean.getCgNumber()).append(delimiter)
                .append(bean.getCgStatus()).append(delimiter)
                .append(bean.getCgCreateWay()).append(delimiter)
                .append(sdf.format(bean.getCreateTime())).append(delimiter)
                .append(sdf.format(bean.getServicingTime())).append(delimiter)
                .append(bean.getCgCustFlag()).append(delimiter)
                .append(bean.getCgInvalidDate()).append(delimiter)
                .append(bean.getUserId()).append(delimiter)
                .append(bean.getBak6());
        try {
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//            byteBuffer.put(sb.toString().getBytes());
//            //创建文件输出流
//            FileOutputStream fileOutputStream = new FileOutputStream(writer.getAbsoutePath());
//            // 创建文件通道
//            FileChannel fileChannel0 = fileOutputStream.getChannel();
//            // 切换到读模式
//            byteBuffer.flip();
//            // 将bytebuffer中的内容写入到通道中
//            fileChannel0.write(byteBuffer);
//            // 关闭资源
//            fileChannel0.close();
//            fileOutputStream.close();
//            Files.write(Paths.get(writer.getAbsoutePath()),sb.toString().getBytes(),StandardOpenOption.APPEND);
            writer.appendLine(sb.toString());
            successCount++;
        } catch (Exception e) {
            logger.info("e:", e);
            failCount++;
        }
    }

    /**
     * 客群失效时间处理
     * @param invalidDate
     * @param createTime
     * @return
     */
    private String convertCgInvalidaDate(String invalidDate,Date createTime){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String invalidDateStr = "";
        if(!StringUtils.isEmpty(invalidDate)){
            Date parseDate = null;
            try{
                parseDate = sdf1.parse(invalidDate);
            }catch (ParseException e){
                try{
                    parseDate = sdf2.parse(invalidDate);
                }catch (ParseException ex){
                    logger.info("解析异常");
                }
            }
            invalidDateStr = DateFormatUtils.format(parseDate,"yyyy-MM-dd");
            return invalidDateStr;
        }else {
            invalidDateStr = DateFormatUtils.format(createTime,"yyyy-MM-dd");
            if(org.apache.commons.lang.StringUtils.isNotBlank(invalidDateStr)){
                Calendar cal = Calendar.getInstance();
                cal.setTime(createTime);
                //创建时间 + 3年
                cal.add(Calendar.YEAR,3);
                invalidDateStr = sdf1.format(cal.getTime());
            }
            return invalidDateStr;
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @param binName
     * @return
     */
    private boolean judgeFile(String path, String binName) {
        try {
            writer = new MyTextFileWriter(binName, path, "GBK", "\r\n");
            if (writer.exist()) {
                Files.delete(Paths.get(writer.getAbsoutePath()));
            }
            writer.open();
        } catch (Exception e) {
            logger.info("");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 校验文件
     *
     * @param fileType
     * @param tempPath
     * @return
     */
    private boolean validateByStream(String fileType, Path tempPath) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        atomicBoolean.get();
        try {
            Files.lines(tempPath).forEach(line -> {
//                String line = null;
                custCount = 0;
                //统一认证号码（18位数字+字母）
                if (CG_FILE_TYPE_1.equals(fileType) && patternValidate(line, 18, NUMBER_LETTER_REGEX)) {
                    custCount++;
                } else if (CG_FILE_TYPE_2.equals(fileType) && patternValidate(line, 15, NUMBER_REGEX)) {
                    //客户编号 15位数字
                    custCount++;
                } else if (CG_FILE_TYPE_3.equals(fileType) && patternValidate(line, 11, NUMBER_REGEX)) {
                    //手机号  11位数字
                    custCount++;
                } else if (CG_FILE_TYPE_4.equals(fileType) && patternValidate(line, 15, NUMBER_LETTER_REGEX)) {
                    //集团编号  15位数字
                    custCount++;
                } else if (CG_FILE_TYPE_5.equals(fileType) && patternValidate(line, 19, NUMBER_REGEX)) {
                    //企业卡号  19位数字
                    custCount++;
                } else {
                    custCount = 0;
                    atomicBoolean.set(false);
//                    atomicBoolean
                }
            });
        } catch (Exception e) {
        }
        return atomicBoolean.get();
    }


    /**
     * 解压缩
     * @param path
     * @return 路径
     */
    private String unZip(Path path) {
        String destinationPath = null;
        String entryName = null;
        try {
            if (path != null) {
                File srcFile = path.toFile();
                if (!srcFile.exists()) {
                    throw new Exception("文件不存在");
                }
                destinationPath = srcFile.getPath().replace(".zip", "");
                //创建压缩文件对象
                ZipFile zipFile = new ZipFile(srcFile);
                //开始解压
                Enumeration<?> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    UUID uuid = UUID.randomUUID();
                    // 如果是文件夹，就创建个文件夹
                    if (entry.isDirectory()) {
                        srcFile.mkdirs();
                    } else {
                        // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                        entryName =  entry.getName();
                        logger.info("uuid.toString()=======:{}",uuid.toString());
                        //这里可以替换原来的名字
                        entryName = entryName.replace(entryName, uuid.toString());
                        File targetFile = new File(destinationPath + File.separator + entryName);
                        // 保证这个文件的父文件夹必须要存在
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        targetFile.createNewFile();
                        // 将压缩文件内容写入到这个文件中
                        InputStream is = zipFile.getInputStream(entry);
                        FileOutputStream fos = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        // 关流顺序，先打开的后关闭
                        fos.close();
                        is.close();
                    }
                }
                zipFile.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  entryName;
    }


    /**
     * 读取文件
     *
     * @param fileType
     * @param destinationPath
     * @return
     */
    private boolean readFiles(String fileType, String destinationPath) {
        boolean validate = true;
        try {
            File srcFile = new File(destinationPath);
            custCount = 0;
            if (srcFile.isDirectory()) {
                File[] next = srcFile.listFiles();
                for (int i = 0; i < next.length; i++) {
                    if (!validate) {
                        break;
                    }
                    if (!next[i].isDirectory()) {
                        try (BufferedReader br = new BufferedReader(new FileReader(next[i]))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                validate = validateZipFileContent(fileType, line);
                                if (!validate) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validate;
    }


    private  void deleteFileAndDirectory(File file){
        boolean deleteFlag = file.delete();
        if(!deleteFlag){
            boolean directory = file.isDirectory();
            if(directory){
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File tempFile = files[i];
                    boolean delete = tempFile.delete();
                    logger.info("删除内部文件--->delete:{}",delete);
                }
                boolean deleteFileFlag = file.delete();
                logger.info("删除内部文件--->delete:{}",deleteFileFlag);
            }
        }
    }

    /**
     * 校验zip文件内容
     * @param fileType
     * @param line
     * @return
     */
    private boolean validateZipFileContent(String fileType, String line) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        try {
            if(!StringUtils.isEmpty(line)){
            //统一认证号码（18位数字+字母）
            if (CG_FILE_TYPE_1.equals(fileType) && patternValidate(line, 18, NUMBER_LETTER_REGEX)) {
                custCount++;
            } else if (CG_FILE_TYPE_2.equals(fileType) && patternValidate(line, 15, NUMBER_REGEX)) {
                //客户编号 15位数字
                custCount++;
            } else if (CG_FILE_TYPE_3.equals(fileType) && patternValidate(line, 11, NUMBER_REGEX)) {
                //手机号  11位数字
                custCount++;
            } else if (CG_FILE_TYPE_4.equals(fileType) && patternValidate(line, 15, NUMBER_LETTER_REGEX)) {
                //集团编号  15位数字
                custCount++;
            } else if (CG_FILE_TYPE_5.equals(fileType) && patternValidate(line, 19, NUMBER_REGEX)) {
                //企业卡号  19位数字
                custCount++;
            } else {
                custCount = 0;
                atomicBoolean.set(false);
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atomicBoolean.get();
    }

    /**
     * @param line
     * @param number
     * @param regex
     * @return fasle-失败  true-成功
     */
    private boolean patternValidate(String line, int number, String regex) {
        boolean flag;
        if (line.trim().length() != number) {
            flag = false;
        } else {
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(line.trim());
            flag = matcher.matches();
        }
        return flag;
    }

}
