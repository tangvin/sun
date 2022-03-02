package com.example.consulconsumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("consul-provider")
public interface HelloCilent {

    @GetMapping("/getHello")
    String getHello(@RequestParam String name);

}