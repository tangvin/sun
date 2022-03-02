package com.example.user.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/11 11:12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Result implements Serializable {


    protected  int resultCode;
    protected String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
