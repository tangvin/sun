package com.example.user.vo;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/4 22:01
 */
public class ThirdPageVo {

    private String buttonSn;
    private String buttonName;

    public ThirdPageVo(String buttonSn,String buttonName){
        this.buttonSn = buttonSn;
        this.buttonName = buttonName;
    }

    public String getButtonSn() {
        return buttonSn;
    }

    public void setButtonSn(String buttonSn) {
        this.buttonSn = buttonSn;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
