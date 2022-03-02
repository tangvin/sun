package com.example.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/paymentConsul")
public class TestController {


    @Value("${server.port}")
    private String port;

    public String paymentService(){
        return "paymentConsul port :"+port +"-"+ UUID.randomUUID().toString();
    }
}
