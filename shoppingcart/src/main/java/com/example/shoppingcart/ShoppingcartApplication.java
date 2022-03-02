package com.example.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient   //Netflix
@EnableDiscoveryClient //spring 自己的
public class ShoppingcartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingcartApplication.class, args);
    }

}
