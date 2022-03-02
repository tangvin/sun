package com.example.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @Description
 * @Author TY
 * @Date 2021/3/14 19:24
 */
public class Consumer {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
//        ctx.start();

        System.out.println("---------spring启动成功--------");
    }


//    @Configuration
//    static class ConsumerConfiguration {
//
//        @Bean
//        public InfoService infoService() throws RemoteException, MalformedURLException, AlreadyBoundException {
//            InfoService infoService = null;
//
//            try {
//                //取远程服务实现
//                infoService = (InfoService) Naming.lookup(InfoService.RMI_URL);
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            }
//
//            return infoService;
//        }
//    }
}
