package com.example.user.batch.rz;

import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.BatchEncodeEnum;
import com.example.user.bean.EiopOmgCustomerGroup;
import com.example.user.dao.EiopOmgCustomerGroupMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author TY
 * @description
 * @date 2021/9/19 9:37
 */
@Component
public class LoadIfdsCustomerGroupFromGtp extends AbstractJob {
    private static final Logger logger = LoggerFactory.getLogger(LoadIfdsCustomerGroupFromGtp.class);

    private static final String FILE_NAME = "BDSP_IFDC_CUST_COUNT";
    private static final String APPLICATION_NAME = "OMGBAT";

    private boolean send = false;
    private int readLineCount = 0;
    private int successCount = 0;
    private int failCount = 0;

    private int deleteCount = 0;
    private char delimiter = 27;
    private int waitTime = 3600;

    @Autowired
    EiopOmgCustomerGroupMapper eiopOmgCustomerGroupMapper;
    @Autowired
    TransactionTemplate transactionTemplate;

    @Override
    public void mainProcess() {
        logger.info("LoadIfdsCustomerGroupFromGtp starting......");
        process();
        printLog();
    }


    private void printLog() {
        logger.info("readLineCount=======",readLineCount);
        logger.info("successCount========",successCount);
        logger.info("failCount==========",failCount);
    }


    private void process() {
        try {
            receiveFile(waitTime);
        } catch (Exception e) {
         logger.info("");
        }finally {
            if(send){
                //发生作业名称到数据

            }
        }

    }


    private void receiveFile(int fileWaitTime) {
        if (fileWaitTime > waitTime) {
            try {
                receiveFileFromGTP(3600);
            } catch (Exception e) {
                logger.error("receiveFile has  exception:{}",e);
            }
        } else {
            receiveFileFromGTP(fileWaitTime - 3600);
        }
    }

    private void receiveFileFromGTP(int fileWaitTime) {
        List<String> filePaths = new ArrayList<>(2);
        filePaths.add("D:\\testBIN\\BDSP_IFDC_CUST_COUNT-20210921.BIN");
        loadFileIntoDB(filePaths);
    }

    /**
     * 加载数据入库
     * @param filePath
     */
    private void loadFileIntoDB(List<String> filePath) {

        List<EiopOmgCustomerGroup> list = new ArrayList<>();
        for (String path : filePath) {
            File file = new File(path);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    EiopOmgCustomerGroup entity;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), BatchEncodeEnum.BATCH_ENCODE_ENUM_GBK.getEncoding()))) {
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            readLineCount++;
                            if (StringUtils.isNotBlank(line)) {
//                                line += Character.toString(delimiter) + "1";
                                String[] split = line.split(Character.toString(delimiter));
                                entity = arrayToEntity(split);
                                list.add(entity);
                                if (!list.isEmpty() && list.size() % 500 == 0) {
                                    //操作数据库
                                    operationDB(list);
                                }
                                System.out.println("readLineCount===" + readLineCount);
                                if (readLineCount % 500 == 0) {
                                    //commit 事务
                                }
                            }
                        }
                    } catch (Exception e) {
                        send = true;
                        failCount++;
                        logger.error("exist exception :{}", e);
                        transactionStatus.setRollbackOnly();
                    }

                }
            });
        }
    }

    /**
     * 操作数据库
     * @param list
     */
    private void operationDB(List<EiopOmgCustomerGroup> list) {
        try {
            for (EiopOmgCustomerGroup eiopOmgCustomerGroup : list) {
                HashMap<Object, Object> paraMap = new HashMap<>(3);
                paraMap.put("cgNumber", eiopOmgCustomerGroup.getCgNumber());
                paraMap.put("bak6", eiopOmgCustomerGroup.getBak6());
                paraMap.put("servicingTime", eiopOmgCustomerGroup.getServicingTime());
                int result = eiopOmgCustomerGroupMapper.updateCustomerCount(paraMap);
                if (result > 0) {
                    //commit
                    successCount++;
                } else {
                    //rollback
                }
            }
            list.clear();
        } catch (Exception e) {
            send = true;
            logger.info("e:{}", e);
        }
    }


    /**
     * 格式化数据
     * @param split
     * @return
     */
    private EiopOmgCustomerGroup arrayToEntity(String[] split) {
        EiopOmgCustomerGroup entity = new EiopOmgCustomerGroup();
        entity.setId(split[0] == null ? null : Long.parseLong(split[0]));
        entity.setCgName(split[1] == null ? "" : split[1]);
        entity.setCgNumber(split[2] == null ? "" : split[2]);
        entity.setBak6(split[3] == null ? "" : split[3]);
        return entity;
    }


    private static String getIpAddres() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("e:{}", e);
        }

        return ip;
    }

}
