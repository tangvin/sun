package com.example.user.dao;

import com.example.user.bean.EventAnalysis;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:09
 */
public interface EventAnalysisMapper {

    /**
     * dasdas
     * @return
     */
    List<EventAnalysis> selectAllInfo();

    List<EventAnalysis> findPageList(Map<String,Object>  param );

    List<Map<String,Object>> selectSpecificPageList(Map<String,String> param);

    List<EventAnalysis> selectPageInfo();
    List<EventAnalysis> selectAreaInfo(String pageSn);

    List<EventAnalysis> selectButtonInfo(@Param("pageSn") String pageSn,@Param("areaSn") String areaSn);

    void insertInfo(EventAnalysis e);

}
