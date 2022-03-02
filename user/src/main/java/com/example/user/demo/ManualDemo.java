//package com.example.user.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallback;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.annotation.PostConstruct;
//import java.util.Map;
//
///**
// * @author TY
// * @description
// * @date 2021/10/21 8:46
// */
//@Service
//public class ManualDemo {
//
//    @Autowired
//    private TransactionTemplate transactionTemplate;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @PostConstruct
//    public void init() {
//        String sql = "replace into money (id, name, money) values (1, '初始化', 100)";
//        jdbcTemplate.execute(sql);
//    }
//
//
//    private boolean doUpdate(int id) throws Exception {
//        if (this.updateName(id)) {
//            this.query("after updateMoney name", id);
//            if (this.updateMoney(id)) {
//                return true;
//            }
//        }
////        return true;
//        throw new Exception("参数异常");
//    }
//
//    private boolean updateName(int id) {
//        String sql = "update money set `name`='更新' where id=" + id;
//        jdbcTemplate.execute(sql);
//        return true;
//    }
//
//
//    private boolean updateMoney(int id) {
//        String sql = "update money set `money`= `money` + 10 where id=" + id;
//        jdbcTemplate.execute(sql);
//        return false;
//    }
//
//    public void query(String tag, int id) {
//        String sql = "select * from money where id=" + id;
//        Map map = jdbcTemplate.queryForMap(sql);
//        System.out.println(tag + " >>>> " + map);
//    }
//
//
//
//
//
//    public void testTransaction(int id) {
//        // 设置隔离级别
//        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
//        // 设置传播属性
//        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        transactionTemplate.execute(new TransactionCallback<Boolean>() {
//            @Override
//            public Boolean doInTransaction(TransactionStatus transactionStatus) {
//                try {
//                    return doUpdate(id);
//                } catch (Exception e) {
//                    transactionStatus.setRollbackOnly();
//                    return false;
//                }
//            }
//        });
//    }
//
//
//
//
//}
