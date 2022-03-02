package com.example.user.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author TY
 * @description
 * @date 2021/10/19 21:10
 */
@Service
public class MybatisUtils {

    @Autowired
    DataSourceTransactionManager transactionManager;


    public void lockStockTranscationManager(){

        try {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();

            MysqlDataSource dataSource = new MysqlDataSource();
            
            Connection connection = dataSource.getConnection();


            SpringManagedTransaction springManagedTransaction = new SpringManagedTransaction(dataSource);

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus transactionStatus = transactionManager.getTransaction(def);
            try{
                transactionManager.commit(transactionStatus);
            }catch (Exception e){
                transactionManager.rollback(transactionStatus);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
