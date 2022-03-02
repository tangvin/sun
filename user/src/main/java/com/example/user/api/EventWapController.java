package com.example.user.api;

import com.example.user.service.EventWapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:59
 */
@RequestMapping("/eventWap")
@RestController
public class EventWapController {

    private final Logger logger = LoggerFactory.getLogger(EventWapController.class);

    @Autowired
    EventWapService eventWapService;


    @RequestMapping("/operationDB")
    public void getEvent(String jsonString) {
        eventWapService.operationDB(jsonString);
    }



}
