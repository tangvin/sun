package com.example.user.batch.rz;

import com.example.user.batch.util.AbstractJob;
import com.example.user.batch.util.BatchEncodeEnum;
import com.example.user.bean.User;
import com.example.user.dao.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TY
 * @description
 * @date 2021/10/20 9:37
 */
@Component
public class LoadUserInfoFromGtp extends AbstractJob {
    private static final Logger logger = LoggerFactory.getLogger(LoadUserInfoFromGtp.class);

    private static final String FILE_NAME = "USER_INFO";
    private static final String APPLICATION_NAME = "OMGBAT";

    private boolean send = false;
    private int readLineCount = 0;
    private int successCount = 0;
    private int failCount = 0;

    private int deleteCount = 0;
    private char delimiter = 27;
    private int waitTime = 3600;

    @Autowired
    UserMapper userMapper;
    @Autowired
    TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

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
        filePaths.add("D:\\testBIN\\USER_INFO-20211020.BIN");
//        loadFileIntoDBTransaction_1(filePaths);
        loadFileIntoDBTransaction_2(filePaths);
//        loadFileIntoDBTransaction_3(filePaths);
    }

    /**
     * 加载数据入库
     * @param filePath
     */
    private void loadFileIntoDBTransaction_1(List<String> filePath) {
        // 设置隔离级别
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        List<User> list = new ArrayList<>();
        for (String path : filePath) {
            File file = new File(path);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                //设置事务传播属性
//                TransactionTemplate.ISOLATION_DEFAULT(TransactionDefinition.PROPAGATION_REQUIRED);
//                // 设置事务的隔离级别,设置为读已提交（默认是ISOLATION_DEFAULT:使用的是底层数据库的默认的隔离级别）
//                transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
//                // 设置是否只读，默认是false
//                transactionTemplate.setReadOnly(true);
//                // 默认使用的是数据库底层的默认的事务的超时时间
//                transactionTemplate.setTimeout(30000);
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    User entity;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), BatchEncodeEnum.BATCH_ENCODE_ENUM_GBK.getEncoding()))) {
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            readLineCount++;
                            if (StringUtils.isNotBlank(line)) {
                                String[] split = line.split(Character.toString(delimiter));
                                entity = arrayToEntity(split);
                                list.add(entity);
                                if (!list.isEmpty() && list.size() % 500 == 0) {
                                    //操作数据库
                                    operationDB(list);
                                }
                                operationDB(list);
                                //commit
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
     * 加载数据入库
     * @param filePath
     */
    private void loadFileIntoDBTransaction_2(List<String> filePath) {
        List<User> list = new ArrayList<>();
        for (String path : filePath) {
            File file = new File(path);
            User entity;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), BatchEncodeEnum.BATCH_ENCODE_ENUM_GBK.getEncoding()))) {
                String line = null;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    readLineCount++;
                    if (StringUtils.isNotBlank(line)) {
                        String[] split = line.split(Character.toString(delimiter));
                        entity = arrayToEntity(split);
                        list.add(entity);
                        if (!list.isEmpty() && list.size() % 5 == 0) {
                            System.out.println("每5次操作operationDB-->count:"+count);
                            //操作数据库
                            operationDB_4(list);
                        }
                    }
                }
                if(!list.isEmpty()){
//                    if(count == 0 ){
//                        throw new Exception("参数异常！");
//                    }
                    System.out.println("list.size=="+list.size());
                    operationDB_4(list);
                    //commit
                }
            } catch (Exception e) {
                send = true;
                failCount++;
                logger.error("exist exception :{}", e);
        }

    }
}


    /**
     * 加载数据入库
     * @param filePath
     */
    private void loadFileIntoDBTransaction_3(List<String> filePath) {
        // 设置隔离级别
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    List<User> list = new ArrayList<>();
                    for (String path : filePath) {
                        File file = new File(path);
                        User entity;
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), BatchEncodeEnum.BATCH_ENCODE_ENUM_GBK.getEncoding()))) {
                            String line = null;
                            int count = 0;
                            while ((line = br.readLine()) != null) {
                                readLineCount++;
                                if (StringUtils.isNotBlank(line)) {
                                    String[] split = line.split(Character.toString(delimiter));
                                    entity = arrayToEntity(split);
                                    list.add(entity);
                                    if (!list.isEmpty() && list.size() % 5 == 0) {
                                        System.out.println("每5次操作operationDB-->count:"+count);
                                        //操作数据库
                                        operationDB_3(list);
                                    }
                                }
                            }
                            if(!list.isEmpty()){
                                if(count == 0 ){
                                    throw new Exception("参数异常！");
                                }
                                System.out.println("list.size=="+list.size());
                                operationDB_3(list);
                            }
                        } catch (Exception e) {
                            send = true;
                            failCount++;
                            logger.error("exist exception :{}", e);
                        }
                    }
                    return true;
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return false;
                }
            }
        });

    }


    /**
     * 操作数据库
     * @param list
     */
    private void operationDB(List<User> list) {
        // 设置隔离级别
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    int count = 0;
                    for (User user : list) {
                        HashMap<Object, Object> paraMap = new HashMap<>(7);
                        paraMap.put("userName", user.getUserName());
                        paraMap.put("realName", user.getRealName());
                        paraMap.put("gender", user.getGender());
                        paraMap.put("phone", user.getPhone());
                        paraMap.put("email", user.getEmail());
                        paraMap.put("bak", user.getBak());
                        paraMap.put("positionId", user.getPositionId());
                        int result = userMapper.insertUser(paraMap);
                        count = count + result ;
//                        if (count == 3) {
//                            System.out.println("count===>3===>:"+count);
//                            throw new Exception("参数异常！！！");
//                        } else {
//                            //rollback
//                        }
                    }
                    list.clear();
                    return true;
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return false;
                }
            }
        });
    }


    /**
     *
     * @param list
     */
    private void operationDB_3(List<User> list) {
        try {
            int count = 0;
            for (User user : list) {
                HashMap<Object, Object> paraMap = new HashMap<>(7);
                paraMap.put("userName", user.getUserName());
                paraMap.put("realName", user.getRealName());
                paraMap.put("gender", user.getGender());
                paraMap.put("phone", user.getPhone());
                paraMap.put("email", user.getEmail());
                paraMap.put("bak", user.getBak());
                paraMap.put("positionId", user.getPositionId());
                int result = userMapper.insertUser(paraMap);
                count = count + result ;
            }
            list.clear();
        } catch (Exception e) {
            logger.error("e:{}",e);
          e.printStackTrace();
        }
    }

    /**
     *
     * @param list
     */
    private void operationDB_4(List<User> list) {
        // 设置隔离级别
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
                        @Override
                        public Boolean doInTransaction(TransactionStatus transactionStatus) {
                            try {

                                int count = 0;
                                int result = userMapper.insertBatch(list);
                                System.out.println("operationDB_4--->result:"+result);
                                System.out.println("operationDB_4--->count:"+count);
                               successCount += result;
                                if(result == 3){
                                    System.out.println("operationDB_4--->参数异常");
                                    System.out.println("operationDB_4--->successCount:"+successCount);
                                    throw new Exception("参数异常");
                                    //commit
                                }else {
//                                    for (User user : list) {
//                                        int i = userMapper.insertUserEntity(user);
//                                        if(i > 0){
//                                            successCount ++;
//                                        }else {
//                                            failCount ++;
//                                        }
//                                    }
                                    System.out.println("count<3");
                                }
                                list.clear();
                                return true;
                            } catch (Exception e) {
                                logger.error("e:{}",e);
                                e.printStackTrace();
                                return false;
                            }
                        }
        });


    }




    /**
     * 格式化数据
     * @param split
     * @return
     */
    private User arrayToEntity(String[] split) {
        User entity = new User();
        entity.setUserName(split[1] == null ? "" : split[1]);
        entity.setRealName(split[2] == null ? "" : split[2]);
        entity.setGender(split[3] == null ? null : split[3]);
        entity.setPhone(split[4] == null ? "" : split[4]);
        entity.setEmail(split[5] == null ? "" : split[5]);
        entity.setBak(split[6] == null ? "" : split[6]);
        entity.setPositionId(split[7] == null ? "" : split[7]);
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
