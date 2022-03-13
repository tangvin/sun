package com.example.vo;

import lombok.Data;

@Data
public class PersonalAccountRelDebitCardVO {

    //单位持卡人编号 -合约表
    private String companyHoldNo;
    //合约编号-分户表
    private String dbcrdArRefno;
    //领卡日期-生命周期表
    private String receiveDate;
    //卡片生命周期-卡片表
    private String cardLifeCycle;
    //借记卡卡号
    private String dbcrdCrdno;
    //可售产品编码-合约表
    private String alsalPdRedno;
    //一户通字-分户表
    private String oneHousehold;
    //定活期标识-分户表
    private String currentIdentification;


}
