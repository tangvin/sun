package com.example.user.api;

import com.example.user.service.DownloadUserInfoService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2020/10/20  10:25
 * @Version: 1.0
 * @Modified: By:
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DownloadUserInfoService downloadUserInfoService;
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    RedisTemplate redisTemplate;

    @RequestMapping("/generatorBinInfo")
    public void generatorBinInfo() {
        downloadUserInfoService.generatorBinInfo();
    }

    @RequestMapping("/loadBinInfo")
    public void loadBinInfo() {
        logger.info("开始调用DownloadUserInfoService");
        downloadUserInfoService.loadBinInfo();
    }


    @RequestMapping("/downloadEpassDeviceWhiteList")
    public void downloadEpassDeviceWhiteList() {
        logger.info("开始调用DownloadUserInfoService");
        downloadUserInfoService.downloadEpassDeviceWhiteList();
    }

//    @RequestMapping("/redis")
//    public String testRedis() {
//        logger.info("开始调用-testRedis");
//        ListOperations listOperations = redisTemplate.opsForList();
//        Object cardName = listOperations.leftPop("cardname");
//        System.out.println("cardName============"+cardName);
//        listOperations.rightPush("type","修","天");
//        return "缓存中的key:+"+""+cardName.toString();
//    }



}
