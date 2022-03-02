package com.example.user.batch.rz;

import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.DateTools;
import com.example.user.batch.util.MyTextFileWriter;
import com.example.user.bean.EiopOmgCustomerGroup;
import com.example.user.dao.EiopOmgCustomerGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * @description
 * @author TY
 * @date 2021/8/28 9:16
 */
@Component
public class DownloadEiopOmgCustomerGroupSetNoFile extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(DownloadEiopOmgCustomerGroupSetNoFile.class);

    private int readCount ;
    private int failCount ;
    private  int successCount ;
    private char delimeter = 27;

    boolean sendFlag = false;
    private final int pageSize = 500;

    private MyTextFileWriter writer = null;

    @Autowired
    private EiopOmgCustomerGroupMapper eiopOmgCustomerGroupMapper;

    @Override
    public void mainProcess() {
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile----->start");
        process();
        printLog();
    }

    public void printLog(){
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile successCount----->:{}", successCount);
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile failCount----->:{}",failCount);
        logger.info("DownloadEiopOmgCustomerGroupSetNoFile----->end");
    }


    public void process(){

        String path = "D:\\testBIN";
        String batchDateStr = DateTools.getCurrentDate();
        String batchDateStart = batchDateStr + " 00:00:00";
        String batchDateEnd = batchDateStr + " 23:59:59";
        Long autoId =  0L;
        //定义临时路径
        String binFileName = "BDSP_IFDC_CUST_COUNT-"+batchDateStr.replaceAll("-","")+".BIN";
        String recordFileName = "BDSP_IFDC_CUST_COUNT-"+batchDateStr.replaceAll("-","")+".BIN";
        try{
            //判断临时文件是否存在
            if(!judgeFile(path,binFileName)){
                sendFlag = true;
                return;
            }
            List<EiopOmgCustomerGroup> list = null;
            HashMap<Object, Object> paraMap = new HashMap<>(5);
            paraMap.put("batchDateStart",batchDateStart);
            paraMap.put("batchDateEnd",batchDateEnd);
            paraMap.put("pageSize",pageSize);
            while(true){
                paraMap.put("autoId",autoId);
                list =  eiopOmgCustomerGroupMapper.selectByPage(paraMap);
                if(null == list ||list.isEmpty() ){
                    break;
                }
                readCount ++ ;
                autoId = list.get(list.size() - 1).getId();
//                formatData(list);
                formatDataTwo(list);
            }

            writer.close();

        }catch (Exception e){
            logger.error("e:{}",e);
            e.printStackTrace();
        }

    }



    private boolean judgeFile(String path,String binName){
        try{
            writer = new MyTextFileWriter(binName,path,"GBK","\r\n");
            if(writer.exist()){
                Files.delete(Paths.get(writer.getAbsoutePath()));
            }
            writer.open();
        }catch (Exception e){
            logger.info("judgeFile has exception:{}",e);
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void formatData(List<EiopOmgCustomerGroup> list){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (EiopOmgCustomerGroup bean : list) {
            StringBuilder sb = new StringBuilder("");
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
            try{
                writer.appendLine(sb.toString());
                successCount ++ ;
            }catch (Exception e){
                logger.info("e:",e);
                failCount ++;
            }

        }
    }

    private void formatDataTwo(List<EiopOmgCustomerGroup> list){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (EiopOmgCustomerGroup bean : list) {
            StringBuilder sb = new StringBuilder("");
            sb.append(bean.getId()).append(delimeter)
                    .append(bean.getCgName()).append(delimeter)
                    .append(bean.getCgNumber()).append(delimeter)
                    .append("56");
            try{
                writer.appendLine(sb.toString());
                successCount ++ ;
            }catch (Exception e){
                logger.info("e:",e);
                failCount ++;
            }

        }
    }
}
