//package com.example.user;
//
////import com.example.user.demo.ManualDemo;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author TY
// * @description
// * @date 2021/10/21 8:53
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TransactionalSample {
//    @Autowired
//    private ManualDemo manualDemo;
//
//
//    @Test
//    public void testManualCase() {
//        System.out.println("======= 编程式事务 start ========== ");
//        manualDemo.query("transaction before", 1);
//        manualDemo.testTransaction(1);
//        manualDemo.query("transaction end", 1);
//        System.out.println("======= 编程式事务 end ========== ");
//    }
//
//}
