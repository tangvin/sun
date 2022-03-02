//package com.example.ribbonconfig;
//
//import com.example.order.config.ExcludeRibbonConfig;
//import com.netflix.loadbalancer.RandomRule;
//import com.netflix.loadbalancer.IRule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Random;
//
///**
// * @ClassName:
// * @Description:自定义Ribbon配置类
// * @Author: Bruce_T
// * @data: 2019/9/6  15:11
// * @Version: 1.0
// * @Modified: By:
// */
//@Configuration
//@ExcludeRibbonConfig
//public class CustomRibbonConfig {
//
//    /**
//     * 策略：随机
//     */
//    @Bean
//    public IRule ribbonRule(){
//        return new RandomRule();
//    }
//}
