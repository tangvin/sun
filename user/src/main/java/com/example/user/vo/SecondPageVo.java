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
public class SecondPageVo {


    private String areaSn;
    private String areaName;

    public SecondPageVo(String areaSn,String areaName){
        this.areaSn = areaSn;
        this.areaName = areaName;
    }


    List<ThirdPageVo> thirdPageVoList = new ArrayList<>();
    //json格式化不显示该字段
    Map<String, ThirdPageVo> buttonSet = new ConcurrentHashMap<>();

//        public Map<String,ThirdPageVo> buttonSet =


    public String getAreaSn() {
        return areaSn;
    }

    public void setAreaSn(String areaSn) {
        this.areaSn = areaSn;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<ThirdPageVo> getThirdPageVoList() {
        return thirdPageVoList;
    }

    public void setThirdPageVoList(List<ThirdPageVo> thirdPageVoList) {
        this.thirdPageVoList = thirdPageVoList;
    }

    public Map<String, ThirdPageVo> getButtonSet() {
        return buttonSet;
    }

    public void setButtonSet(Map<String, ThirdPageVo> buttonSet) {
        this.buttonSet = buttonSet;
    }

    public void setbuttonToButtonSet(ThirdPageVo thirdPageVo){
        ThirdPageVo put = this.buttonSet.put(thirdPageVo.getButtonSn(), thirdPageVo);
        if(put  == null){
            this.thirdPageVoList.add(thirdPageVo);
        }
    }
}
