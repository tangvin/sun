package com.example.consulconsumer.controller;

import com.example.consulconsumer.feign.HelloCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HelloCilent helloCilent;

    @GetMapping("restTemplateInvoke")
    public ResponseEntity<String> restTemplateInvoke(@RequestParam String name) {
        String url = "http://consul-provider/getHello?name=" + name;
        String result = this.restTemplate.getForObject(url, String.class);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("feignInvoke")
    public ResponseEntity<String> feignInvoke(@RequestParam String name) {
        String result = helloCilent.getHello(name);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
