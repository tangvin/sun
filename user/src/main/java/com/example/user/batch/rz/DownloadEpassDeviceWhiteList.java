package com.example.user.batch.rz;

import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.DateTools;
import com.example.user.batch.util.MyTextFileWriter;
import com.example.user.bean.EiopEpassDeviceWhiteList;
import com.example.user.dao.EiopEpassDeviceWhiteListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author TY
 * @description
 * @date 2021/10/30 10:15
 */
@Component
public class DownloadEpassDeviceWhiteList extends AbstractJob {


    private static final Class<DownloadEpassDeviceWhiteList> clazz = DownloadEpassDeviceWhiteList.class;
    private static final String MY_CALSS_NAME = clazz.getName();
    private static final Logger LOGGER = LoggerFactory.getLogger(clazz);
    public static final String FILE_TYPE_TXT = "txt";
    public static final String FILE_TYPE_CSV = "csv";
    private int readCount;
    private int failCount;
    private int successCount;
    private int pageSize = 500;
    private boolean sendFlag ;


    String path = "D:\\testBIN";


    private MyTextFileWriter writer = null;

    @Autowired
    EiopEpassDeviceWhiteListMapper eiopEpassDeviceWhiteListMapper;

    @Override
    public void mainProcess() {
        super.mainProcess();
        process();
        printLog();
    }

    public void printLog() {
        LOGGER.info("DownloadEiopOmgCustomerGroupSetNoFile successCount----->:{}", successCount);
        LOGGER.info("DownloadEiopOmgCustomerGroupSetNoFile failCount----->:{}", failCount);
        LOGGER.info("DownloadEiopOmgCustomerGroupSetNoFile----->end");
    }


    private void process(){
        String batchDateStr = DateTools.getCurrentDate();
        String batchDateStart = batchDateStr + " 00:00:00";
        String batchDateEnd = batchDateStr + " 23:59:59";

       //定义临时路径
        String binFileName = "EPASSWHITELIST-"+batchDateStr.replaceAll("-","")+".BIN";
        try {
            //判断临时文件是否存在
            if (!judgeFile(path, binFileName)) {
                sendFlag = true;
                return;
            }
            Long autoId =  0L;
            List<EiopEpassDeviceWhiteList> list;
            HashMap<Object, Object> paraMap = new HashMap<>(5);
            paraMap.put("batchDateStart",batchDateStart);
            paraMap.put("batchDateEnd",batchDateEnd);
            paraMap.put("status","0");
            paraMap.put("pageSize",pageSize);
            while(true){
                paraMap.put("autoId",autoId);
                list =  eiopEpassDeviceWhiteListMapper.selectEpassDeviceWhiteList(paraMap);
                if(null == list ||list.isEmpty() ){
                    break;
                }
                readCount ++ ;
                autoId = list.get(list.size() - 1).getId();
                //解析拼装数据
                processFileData(list);
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public void  processFileData(List<EiopEpassDeviceWhiteList> list) throws Exception{
        if(!CollectionUtils.isEmpty(list)){
            LOGGER.info("");
            for (EiopEpassDeviceWhiteList deviceWhiteList : list) {
                Path tempPath = null;
                String fileType = deviceWhiteList.getFileType();
                //通过oaas获取文件流
                String localPath1 = "D:\\testBIN\\测试1031.txt";
                File localFile = new File(localPath1);
                FileInputStream fileInputStream = new FileInputStream(localFile);

                if(FILE_TYPE_TXT.equals(fileType) || FILE_TYPE_CSV.equals(fileType)){
                    UUID uuid = UUID.randomUUID();
                    String localPath2 = "D:\\path222";
                    tempPath = Paths.get(localPath2, File.separator + uuid.toString() + ".txt");
                    Files.copy(fileInputStream, tempPath);
                    try(Stream stream = Files.lines(tempPath, Charset.forName("GBK"))){
                        stream.forEachOrdered(line ->{
                            if(!StringUtils.isEmpty(line)){
                                String[] split = line.toString().split(",");
                                //客编
                                String value1 = split[0];
                                //渠道
                                String value2 = split[1];
                                //日期
                                String value3 = split[2];
                                deviceWhiteList.setFileContentNumber(value1);
                                deviceWhiteList.setFileContentChannel(value2);
                                deviceWhiteList.setFileContentDate(value3);
                                generateBinFile(deviceWhiteList);
                            }
                        });
                    }catch (Exception e){
                        LOGGER.error("exception:{}",e);
                    }
                }

            }

        }
    }


    private void generateBinFile(EiopEpassDeviceWhiteList entity){
        char delimiter = 27;
        StringBuilder sb = new StringBuilder("");
        sb.append(entity.getFileContentNumber()).append(delimiter)
                .append(entity.getFileContentChannel()).append(delimiter)
                .append(entity.getFileContentDate()).append(delimiter)
                .append(entity.getTellerNo()).append(delimiter)
                .append(entity.getApproveTellerNo());
        try{
            writer.appendLine(sb.toString());
            successCount ++;
        }catch (Exception e){
            LOGGER.error("e:{}",e);
            failCount ++;
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
            LOGGER.info("");
            e.printStackTrace();
            return false;
        }
        return true;
    }



}
