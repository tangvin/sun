package com.example.user.batch.rz;

import com.alibaba.fastjson.JSON;
import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.MyTextFileWriter;
import com.example.user.bean.EiopOmgCustomerGroup;
import com.example.user.dao.EiopOmgCustomerGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * @description 客群白名单重构方案1 ：已知文件的格式，然后进行判断
 * @date 2021/9/30 9:45
 */
@Component
public class DownloadWhiteListRefactorNo3 extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(DownloadEiopOmgCustomerWhiteListSetNoFile.class);

    private int failCount;
    private int successCount;
    private boolean sendFlag;
    private int custCount;
    public static final String CG_STATUS_FALSE = "0";
    public static final String CG_STATUS_TWO = "1";
    public static final String CG_STATUS_ONE = "2";
    public static final String CG_NUMBER = "CG_NUMBER";
    public static final String CG_STATUS = "CG_STATUS";
    public static final String VERSION = "VERSION";
    public static final String CG_CREATE_WAY = "2";
    public static final String SAVE_CONTENT = "1";
    public static final String EXECUTE_STATUS = "0";

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
        String localPath1 = "D:\\path111\\111.txt";
        String localPath2 = "D:\\path222\\222.bin";

        //定义临时路径
        String binFileName = "OMGCOUPONDTL" + batchDateStr.replaceAll("-", "") + ".BIN";
        String recordFileName = "OMGCOUPONDTL" + batchDateStr.replaceAll("-", "") + ".BIN";

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
                    UUID uuid = UUID.randomUUID();
                    Path tempPath = Paths.get(localPath2, File.separator + uuid.toString()+".txt");

                    try {
                        //通过oaas获取文件流
                        String localPath = "D:\\testBIN\\test.txt";
                        File localFile = new File(localPath);
                        FileInputStream fileInputStream = new FileInputStream(localFile);
                        File outFile = new File(localPath2);
                        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                        //

                        FileChannel inChannel = fileInputStream.getChannel();
                        FileChannel outChannel = fileOutputStream.getChannel();
                        inChannel.transferTo(0,inChannel.size(),outChannel);
                        reader = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));

                        String cgfileType = (String) map.get("cg_file_type");
                        logger.info("-----------------cgfileType:{}", cgfileType);
                        //获取本地文件
                        if ("txt".equals(cgfileType)) {
                            //文件写到本地
                            Files.copy(fileInputStream, tempPath);
//                            new File(fileInputStream,tempPath.toString());
                        }else if("zip".equals(cgfileType)){

                        }
                        //校验本地文件
//                        boolean validateContent = validateContent(cgfileType, fileInputStream);
                        boolean validateContent = validateByStream(cgfileType, tempPath);

                        if (validateContent) {
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
                                String fileId = String.valueOf(map.get("file_id"));
                                String fileType = "zip";
                                if (fileType.contains("zip")) {
                                    File file = new File(path, map.get(CG_NUMBER) + ".zip");
                                    if (!file.exists()) {
                                        file.createNewFile();
                                    }
                                    outputStream = new FileOutputStream(file);
                                    byte[] bytes = new byte[1024];
                                    int index;
                                    while ((index = inputStream.read(bytes)) != -1) {
                                        outputStream.write(bytes, 0, index);
                                        outputStream.flush();
                                    }
                                    zipFile = new ZipFile(file);
                                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                                    while (entries.hasMoreElements()) {
                                        ZipEntry zipEntry = entries.nextElement();
                                        inputStream = zipFile.getInputStream(zipEntry);
                                        reader = new BufferedReader(new InputStreamReader(inputStream));
                                        String userId = null;
                                        while ((userId = reader.readLine()) != null) {
                                            map.put("userId", userId);
                                            //map转实体
                                            EiopOmgCustomerGroup eiopOmgCustomerGroup = JSON.parseObject(toJSONString(map), EiopOmgCustomerGroup.class);
                                            formatData(eiopOmgCustomerGroup);
                                        }
                                    }
                                    file.delete();
                                } else if (fileType.contains("txt") || fileType.contains("csv")) {
                                    ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>(map);
                                    Files.lines(tempPath).forEach(line -> {
                                        concurrentHashMap.put("userId", line);
                                        //map转实体
                                        EiopOmgCustomerGroup eiopOmgCustomerGroup = JSON.parseObject(toJSONString(concurrentHashMap), EiopOmgCustomerGroup.class);
                                        formatData(eiopOmgCustomerGroup);
                                    });
                                }
                            }
                        } else {
                            //更新状态为  5 -校验失败
                            int update = eiopOmgCustomerGroupMapper.updateStatusValidateFail((Long) map.get("id"));
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
        char delimeter = 27;
        StringBuilder sb = new StringBuilder("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append(bean.getId()).append(delimeter)
                .append(bean.getCgName()).append(delimeter)
                .append(bean.getCgNumber()).append(delimeter)
                .append(bean.getCgStatus()).append(delimeter)
                .append(bean.getCgCreateWay()).append(delimeter)
                .append(sdf.format(bean.getCreateTime())).append(delimeter)
                .append(sdf.format(bean.getServicingTime())).append(delimeter)
                .append(bean.getCgCustFlag()).append(delimeter)
                .append(bean.getCgInvalidDate()).append(delimeter)
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
     * 校验文件内容
     *
     * @param type
     * @param inputStream
     * @return
     */
    private boolean validateContent(String type, InputStream inputStream) {
        boolean flag = true;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            custCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                //统一认证号码（18位数字+字母）
                if (CG_FILE_TYPE_1.equals(type) && patternValidate(line, 18, NUMBER_LETTER_REGEX)) {
                    custCount++;
                } else if (CG_FILE_TYPE_2.equals(type) && patternValidate(line, 15, NUMBER_REGEX)) {
                    //客户编号 15位数字
                    custCount++;
                } else if (CG_FILE_TYPE_3.equals(type) && patternValidate(line, 11, NUMBER_REGEX)) {
                    //手机号  11位数字
                    custCount++;
                } else if (CG_FILE_TYPE_4.equals(type) && patternValidate(line, 15, NUMBER_LETTER_REGEX)) {
                    //集团编号  15位数字
                    custCount++;
                } else if (CG_FILE_TYPE_5.equals(type) && patternValidate(line, 19, NUMBER_REGEX)) {
                    //企业卡号  19位数字
                    custCount++;
                } else {
                    custCount = 0;
                    flag = false;
                }
            }
        } catch (Exception e) {
            logger.error("校验失败:{}", e);
        }
        return flag;
    }


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
     * @param line
     * @param number
     * @param regex
     * @return
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
