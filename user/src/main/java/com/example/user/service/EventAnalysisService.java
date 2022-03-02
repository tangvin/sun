package com.example.user.service;

import com.example.user.bean.EventAnalysis;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:24
 */
public interface EventAnalysisService {


    List<EventAnalysis> getEventInfo();

    /**
     * lambda表达式流处理
     *
     * @return
     */
    List<EventAnalysis> lambdaSelectList();

    /**
     * lambda表达式流处理所有数据
     *
     * @return
     */
    List<EventAnalysis> lambdaFindList(Map param);



    List<Map<String,Object>> selectSpecificPageList(Map<String,String> param);
}
