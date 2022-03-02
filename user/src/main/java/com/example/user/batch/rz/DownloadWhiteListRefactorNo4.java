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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author TY
 * @description 客群白名单重构方案4 ：已知文件的格式
 * @date 2021/9/30 9:45
 */
@Component
public class DownloadWhiteListRefactorNo4 extends AbstractJob {

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

    /**
     * 校验数字
     **/
    public static final String NUMBER_REGEX = "^[0-9]*$";
    /**
     * 校验数字和字母
     **/
    public static final String NUMBER_LETTER_REGEX = "^[A-Za-z0-9]+$";
    private List<String> zipLineList = null;


    private MyTextFileWriter writer = null;
    @Autowired
    EiopOmgCustomerGroupMapper eiopOmgCustomerGroupMapper;

    @Override
    public void mainProcess() {
        super.mainProcess();
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile----->start");
        process();
        printLog();
    }

    public void printLog() {
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile successCount----->:{}", successCount);
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile failCount----->:{}", failCount);
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile----->end");
    }


    public void process() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        FileOutputStream outputStream = null;
        ZipFile zipFile = null;
        String batchDateStr = "";
        String path = "D:\\testBIN";
//        String localPath1 = "D:\\path111\\111.txt";
        String localPath2 = "D:\\path222";

        boolean isOk = false;
        String BinName = "TANGYIN";
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
                        //文件内容
                        String cgfileType = (String) map.get("cg_file_type");
                        //文件格式
                        String fileType = "zip";
                        //获取本地文件
                        if ("txt".equals(fileType)) {
                            tempPath = Paths.get(localPath2, File.separator + uuid.toString() + ".txt");
                            Files.copy(fileInputStream, tempPath);
                            isOk = validateByStream(cgfileType, tempPath);
                        } else if ("zip".equals(fileType)) {
                            tempPath = Paths.get(localPath2, File.separator + uuid.toString() + ".zip");
                            Files.copy(fileInputStream, tempPath);
                            String destinationPath = unZip(tempPath);
                            isOk = readFiles(cgfileType, destinationPath);
                        }
                        if (isOk) {
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
                                logger.info("amount==={}", amount);
                                if (fileType.contains("zip")) {
                                    if(zipLineList != null && zipLineList.size() > 0){
                                        logger.info("zipLineList.size---->:{}",zipLineList.size());
                                        zipLineList.forEach(line->{
                                            concurrentHashMap.put("userId", line);
                                            EiopOmgCustomerGroup eiopOmgCustomerGroup = JSON.parseObject(toJSONString(concurrentHashMap), EiopOmgCustomerGroup.class);
                                            formatData(eiopOmgCustomerGroup);
                                        });
                                    }
                                } else if (fileType.contains("txt") || fileType.contains("csv")) {
                                    Files.lines(tempPath).forEach(line -> {
                                        concurrentHashMap.put("userId", line);
                                        EiopOmgCustomerGroup eiopOmgCustomerGroup = JSON.parseObject(toJSONString(concurrentHashMap), EiopOmgCustomerGroup.class);
                                        formatData(eiopOmgCustomerGroup);
                                    });
                                }
                            }
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
                    }
                }
            }
        } catch (Exception e) {
            logger.error("循环出现异常:{}", e);
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                logger.error("e======:{}", e);
                e.printStackTrace();
            }
            if (sendFlag) {
                logger.error("出现异常了 ~~~~~~~~~");
            }
            logger.info("99999999999999999999");
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
                    // 如果是文件夹，就创建个文件夹
                    if (entry.isDirectory()) {
                        srcFile.mkdirs();
                    } else {
                        // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                        File targetFile = new File(destinationPath + "/" + entry.getName());
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destinationPath;
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
        BufferedReader br = null;
        try {
            zipLineList = new ArrayList<>();
            File srcFile = new File(destinationPath);
            custCount = 0;
            if (srcFile.isDirectory()) {
                File[] next = srcFile.listFiles();
                for (int i = 0; i < next.length; i++) {
                    if (!validate) {
                        break;
                    }
                    if (!next[i].isDirectory()) {
                        br = new BufferedReader(new FileReader(next[i]));
                        String line;
                        while ((line = br.readLine()) != null) {
                            validate = validateZipFile(fileType, line);
                            if (!validate) {
                                break;
                            }
                            if(!StringUtils.isEmpty(line)){
                                zipLineList.add(line);
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(br != null){
                    br.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return validate;
    }


    /**
     * 校验zip文件内容
     * @param fileType
     * @param line
     * @return
     */
    private boolean validateZipFile(String fileType, String line) {
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
