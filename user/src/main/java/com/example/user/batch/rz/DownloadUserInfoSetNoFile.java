package com.example.user.batch.rz;

import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.DateTools;
import com.example.user.batch.util.MyTextFileWriter;
import com.example.user.bean.User;
import com.example.user.dao.UserMapper;
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
public class DownloadUserInfoSetNoFile extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(DownloadUserInfoSetNoFile.class);

    private int failCount ;
    private  int successCount ;
    private char delimeter = 27;

    boolean sendFlag = false;
    private final int pageSize = 500;

    private MyTextFileWriter writer = null;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void mainProcess() {
        logger.info("DownloadUserInfoSetNoFile----->start");
        process();
        printLog();
    }

    public void printLog(){
        logger.info("DownloadUserInfoSetNoFile successCount----->:{}", successCount);
        logger.info("DownloadUserInfoSetNoFile failCount----->:{}",failCount);
        logger.info("DownloadUserInfoSetNoFile----->end");
    }


    public void process(){
        String path = "D:\\testBIN";
        String batchDateStr = DateTools.getCurrentDate();
        Integer autoId =  0;
        //定义临时路径
        String binFileName = "USER_INFO-"+batchDateStr.replaceAll("-","")+".BIN";
        String recordFileName = "USER_INFO-"+batchDateStr.replaceAll("-","")+".BIN";
        try{
            //判断临时文件是否存在
            if(!judgeFile(path,binFileName)){
                sendFlag = true;
                return;
            }
            List<User> list = null;
            HashMap<Object, Object> paraMap = new HashMap<>(5);
            paraMap.put("pageSize",pageSize);
            while(true){
                paraMap.put("autoId",autoId);
                list =  userMapper.selectByPage(paraMap);
                if(null == list ||list.isEmpty() ){
                    break;
                }
                autoId = list.get(list.size() - 1).getId();
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


    private void formatDataTwo(List<User> list){
        for (User bean : list) {
            StringBuilder sb = new StringBuilder("");
            sb.append(bean.getId()).append(delimeter)
                    .append(bean.getUserName()).append(delimeter)
                    .append(bean.getRealName()).append(delimeter)
                    .append(bean.getGender()).append(delimeter)
                    .append(bean.getPhone()).append(delimeter)
                    .append(bean.getEmail()).append(delimeter)
                    .append(bean.getBak()).append(delimeter)
                    .append(bean.getPositionId());
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
