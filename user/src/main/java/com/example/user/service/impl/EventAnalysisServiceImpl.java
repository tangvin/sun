package com.example.user.service.impl;

import com.example.user.bean.EventAnalysis;
import com.example.user.dao.EventAnalysisMapper;
import com.example.user.service.EventAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:24
 */
@Service
public class EventAnalysisServiceImpl implements EventAnalysisService {

    @Autowired
    EventAnalysisMapper eventAnalysisMapper;

    @Override
    public List<EventAnalysis> getEventInfo() {
        //1-页面集合
        List<EventAnalysis> pageInfoList = eventAnalysisMapper.selectPageInfo();
        List<EventAnalysis> pageList = new ArrayList<>(pageInfoList.size());
        if (pageInfoList.isEmpty()) {
            return pageList;
        }
        for (EventAnalysis pageInfo : pageInfoList) {
            EventAnalysis page = new EventAnalysis();
            page.setPageSn(pageInfo.getPageSn());
            page.setPageName(pageInfo.getPageName());
            //2-某一个页面下的区域集合
            List<EventAnalysis> areaInfoList = eventAnalysisMapper.selectAreaInfo(pageInfo.getPageSn());
            System.out.println("areaInfoList.size()==" + areaInfoList.size());
            if (!areaInfoList.isEmpty()) {
                List<EventAnalysis> areaList = new ArrayList<>(areaInfoList.size());
                for (EventAnalysis areaInfo : areaInfoList) {
                    EventAnalysis area = new EventAnalysis();
                    area.setAreaSn(areaInfo.getAreaSn());
                    area.setAreaName(areaInfo.getAreaName());

                    //3-指定页面和区域下的按钮集合
                    List<EventAnalysis> buttonInfoList = eventAnalysisMapper.selectButtonInfo(pageInfo.getPageSn(), areaInfo.getAreaSn());
                    System.out.println("buttonInfoList.size()==" + buttonInfoList.size());
                    if (!buttonInfoList.isEmpty()) {
                        List<EventAnalysis> buttonList = new ArrayList<>(buttonInfoList.size());
                        for (EventAnalysis buttonInfo : buttonInfoList) {
                            EventAnalysis button = new EventAnalysis();
                            button.setButtonSn(buttonInfo.getButtonSn());
                            button.setButtonName(buttonInfo.getButtonName());
                            buttonList.add(button);
                        }
                        area.setChildList(buttonList);
                    }
                    areaList.add(area);
                }
                page.setChildList(areaList);
            }
            pageList.add(page);
        }
        //json格式化
        System.out.println("pageList---" + pageList.size());
        return pageList;
    }


    /**
     * lambda流处理
     *
     * @return
     */
    @Override
    public List<EventAnalysis> lambdaSelectList() {
        List<EventAnalysis> pageInfoList = eventAnalysisMapper.selectPageInfo();
        if (pageInfoList.isEmpty()) {
            return new ArrayList<>(0);
        }
        // 获取一级目录
        List<EventAnalysis> newPageList = pageInfoList.stream().map(i -> {
            EventAnalysis eal = new EventAnalysis();
            eal.setPageSn(i.getPageSn());
            eal.setPageName(i.getPageName());
            return eal;
        }).collect(Collectors.toList());
        // 遍历一级目录
        newPageList.stream().forEach(j -> {
            // 获取二级目录
            List<EventAnalysis> areaInfoList = eventAnalysisMapper.selectAreaInfo(j.getPageSn());
            if (!areaInfoList.isEmpty()) {
                List<EventAnalysis> newAreaList = areaInfoList.stream().map(eal -> {
                    EventAnalysis areaSis = new EventAnalysis();
                    areaSis.setAreaSn(eal.getAreaSn());
                    areaSis.setAreaName(eal.getAreaName());
                    return areaSis;
                }).collect(Collectors.toList());
                // 遍历二级目录
                areaInfoList.stream().forEach(k -> {
                    // 获取三级目录
                    List<EventAnalysis> buttonInfoList = eventAnalysisMapper.selectButtonInfo(j.getPageSn(), j.getAreaSn());
                    if (!buttonInfoList.isEmpty()) {
                        List<EventAnalysis> newButtonList = buttonInfoList.stream().map(button -> {
                            EventAnalysis buttonSis = new EventAnalysis();
                            buttonSis.setButtonSn(button.getButtonSn());
                            buttonSis.setButtonName(button.getButtonName());
                            return buttonSis;
                        }).collect(Collectors.toList());
                        k.setChildList(newButtonList);
                    }
                });
                // 存储二级及三级目录
                j.setChildList(newAreaList);
            }
        });
        //json格式化
        System.out.println("newPageList---" + newPageList.size());
        return newPageList;
    }


    /**
     * lambda流处理所有数据
     *
     * @return
     */
    @Override
    public List<EventAnalysis> lambdaFindList(Map param) {
        List<EventAnalysis> findPageList = eventAnalysisMapper.findPageList(param);
        if (findPageList.isEmpty()) {
            return new ArrayList<>(0);
        }
        // 获取一级目录去重
        List<EventAnalysis> newPageList = findPageList.stream().map(i -> {
            EventAnalysis ealSis = new EventAnalysis();
            ealSis.setPageSn(i.getPageSn());
            ealSis.setPageName(i.getPageName());
            ealSis.setPageNo(i.getPageNo());
            return ealSis;
        }).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> p.getPageSn()))), ArrayList::new));
        // 遍历一级目录
        newPageList.stream().forEach(j -> {
            // 获取二级目录
            List<EventAnalysis> newAreaList = findPageList.stream()
                    .filter(areaF -> areaF.getPageSn().equals(j.getPageSn()))
                    .map(areaM -> {
                        EventAnalysis areaSis = new EventAnalysis();
                        areaSis.setAreaSn(areaM.getAreaSn());
                        areaSis.setAreaName(areaM.getAreaName());
                        areaSis.setAreaNo(areaM.getAreaNo());
                        return areaSis;
                    }).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> p.getAreaSn()))), ArrayList::new));
            // 遍历二级目录
            newAreaList.stream().forEach(k -> {
                // 获取三级目录
                List<EventAnalysis> newButtonList = findPageList.stream()
                        .filter(buttonF -> buttonF.getPageSn().equals(j.getPageSn()) && buttonF.getAreaSn().equals(k.getAreaSn()))
                        .map(buttonM -> {
                            EventAnalysis buttSis = new EventAnalysis();
                            buttSis.setButtonSn(buttonM.getButtonSn());
                            buttSis.setButtonName(buttonM.getButtonName());
                            buttSis.setButtonNo(buttonM.getButtonNo());
                            buttSis.setButtonSeq(buttonM.getButtonSeq());
                            buttSis.setPublishTime(buttonM.getPublishTime());
                            buttSis.setSerialNo(buttonM.getSerialNo());
                            return buttSis;
                        }).collect(Collectors.toList());
                k.setChildList(newButtonList);
            });
            // 存储二级及三级目录
            j.setChildList(newAreaList);
        });
        //json格式化
        System.out.println("Lambda----newPageList：---" + newPageList.size());
        return newPageList;
    }

    @Override
    public List<Map<String,Object>> selectSpecificPageList(Map<String, String> param) {
        System.out.println("param.get(level)=="+param.get("level"));
        List<Map<String, Object>> retMap = eventAnalysisMapper.selectSpecificPageList(param);
        return retMap;
    }

}
