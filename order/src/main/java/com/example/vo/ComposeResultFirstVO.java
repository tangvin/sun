package com.example.vo;

import lombok.Data;

@Data
public class ComposeResultFirstVO {

    //合约编号
    private String contractNo;
    //客户号
    private String cusNo;
    //卡片生命周期
    private String cardLifeCycle;

    //可售产品编码
    private String saleableProductCode;
    //单位持卡人编号
    private String companyHoldNo;
    //发行日期
    private String issueDate;

}
