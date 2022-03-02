package com.example.consul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@RequestMapping("/provider")
public class ProviderController {


    private static final int port = 1003;

    @GetMapping("getHello")
    public String getHello(@RequestParam String name) throws Exception {
        return String.format("[%s:%s] Hello %s", InetAddress.getLocalHost().getHostName(), this.port, name);
    }
}
