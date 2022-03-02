package com.example.user.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/4 22:00
 */
public class FirstPageVo {


    private String pageSn;
    private String pageName;

    public FirstPageVo(String pageSn,String pageName){
        this.pageSn = pageSn;
        this.pageName = pageName;
    }


    List<SecondPageVo> secondPageVoList = new ArrayList<>();
    //json格式化不显示该字段
    Map<String, SecondPageVo> areaSet = new ConcurrentHashMap<>();


    public String getPageSn() {
        return pageSn;
    }

    public void setPageSn(String pageSn) {
        this.pageSn = pageSn;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<SecondPageVo> getSecondPageVoList() {
        return secondPageVoList;
    }

    public void setSecondPageVoList(List<SecondPageVo> secondPageVoList) {
        this.secondPageVoList = secondPageVoList;
    }

    public Map<String, SecondPageVo> getAreaSet() {
        return areaSet;
    }

    public void setAreaSet(Map<String, SecondPageVo> areaSet) {
        this.areaSet = areaSet;
    }

    public void setAreaToAreaSet(SecondPageVo secondPageVo){
        SecondPageVo put = this.areaSet.put(secondPageVo.getAreaSn(), secondPageVo);
        if(put == null){
            secondPageVoList.add(secondPageVo);
        }
    }
}
