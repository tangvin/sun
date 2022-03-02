package com.example.user.service.impl;

import com.example.user.bean.EventAnalysis;
import com.example.user.dao.EventAnalysisMapper;
import com.example.user.service.EventVoService;
import com.example.user.vo.EventAnalysisVo;
import com.example.user.vo.FirstPageVo;
import com.example.user.vo.SecondPageVo;
import com.example.user.vo.ThirdPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/4 15:02
 */
@Service
public class EventVoServiceImpl implements EventVoService {

    @Autowired
    EventAnalysisMapper eventAnalysisMapper;

    @Override
    public EventAnalysisVo getEventAnalysisVo() {
        List<EventAnalysis> eventAnalyses = eventAnalysisMapper.selectAllInfo();
        Map<String,FirstPageVo> firstPageSet = new ConcurrentHashMap<>();
        eventAnalyses.forEach(bean ->{
            String pageSn = bean.getPageSn();
            String areaSn = bean.getAreaSn();
            String buttonSn = bean.getButtonSn();


            FirstPageVo firstPageVo = new FirstPageVo(pageSn, bean.getPageName());
            SecondPageVo secondPageVo = new SecondPageVo(areaSn, bean.getAreaName());
            ThirdPageVo thirdPageVo = new ThirdPageVo(buttonSn, bean.getButtonName());

            FirstPageVo firstPageVoFromMap = firstPageSet.get(pageSn);

            //若一级菜单为空，则直接将二级菜单和三级菜单添加进去
            if(firstPageVoFromMap == null){
                secondPageVo.setbuttonToButtonSet(thirdPageVo);
                firstPageVo.setAreaToAreaSet(secondPageVo);

                firstPageVo.getAreaSet().put(areaSn,secondPageVo);

                firstPageSet.put(pageSn,firstPageVo);

            }
            //若二级菜单为空，则直接将三级菜单添加进去
            Map<String, SecondPageVo> areaSet = firstPageVoFromMap.getAreaSet();
            SecondPageVo secondPageVoFromMap = areaSet.get(areaSn);
            if(secondPageVoFromMap == null){
                secondPageVo.setbuttonToButtonSet(thirdPageVo);
                firstPageVoFromMap.setAreaToAreaSet(secondPageVo);
            }

            //r若三级菜单为空，则只添加三级标签
            Map<String, ThirdPageVo> buttonSet = secondPageVoFromMap.getButtonSet();
            ThirdPageVo thirdPageVoFromMap = buttonSet.get(buttonSn);
            if(thirdPageVoFromMap == null){
                secondPageVo.setbuttonToButtonSet(thirdPageVo);
            }

        });
            //若都有则不用添加
            //处理返回值
            EventAnalysisVo eventAnalysisVo = new EventAnalysisVo();
            firstPageSet.values().forEach(firstPageVo -> {
                eventAnalysisVo.setFirstPageVoToList(firstPageVo);
            });

            System.out.println("eventAnalysisVo==="+eventAnalysisVo);

            return eventAnalysisVo;
    }
}
