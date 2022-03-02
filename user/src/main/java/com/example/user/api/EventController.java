package com.example.user.api;

import com.alibaba.fastjson.JSON;
import com.example.user.bean.EventAnalysis;
import com.example.user.service.EventAnalysisService;
import com.example.user.service.EventVoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:59
 */
@RequestMapping("/event")
@RestController
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventAnalysisService eventAnalysisService;

    @Autowired
    private EventVoService eventVoService;

    @RequestMapping("/getEventInfo")
    public Map<String,Object> getEvent(Map<String,String> param) {
        logger.debug("start........");
        long s1 = System.currentTimeMillis();
        param.put("pageName","幸福生活首页");
        param.put("areaName","");
        param.put("buttonName",null);
        Map<String,Object> retMap = new HashMap<>();

        if(!param.isEmpty()){
            //参数为空，查询所有
            List<EventAnalysis> eventInfoList = eventAnalysisService.lambdaFindList(param);
            retMap.put("result",eventInfoList);
        }else {
            //返回指定页面的拼接数据
            List<Map<String, Object>> eventInfoMap = eventAnalysisService.selectSpecificPageList(param);
            retMap.put("result",eventInfoMap);
        }

//        Object json = JSON.toJSON(retMap);
        retMap.put("result",JSON.toJSON(retMap));
        retMap.put("code","1");
        long s2 = System.currentTimeMillis();
        System.out.println("处理时间ms：" + (s2 - s1));
        return retMap;
    }


//    @RequestMapping("/getEventAnalysisVo")
//    public EventAnalysisVo updateInfo() {
//        logger.debug("getEventAnalysisVo........start...........");
//        EventAnalysisVo eventAnalysisVo = eventVoService.getEventAnalysisVo();
//        return eventAnalysisVo;
//    }
}
