package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DebitCardContractBO {

    //法人编码
    private String lgperCode;
    //合约编号
    private String dbcrdArRefno;



    //客户号
    private String cusno;

    //可售产品编码
    private String alsalPdRedno;
    //单位持卡人编号
    private String cscdCrdhlRefno;
    //发行日期
    private String issueDate;



}
