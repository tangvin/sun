package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DebitCardBO {

    //借记卡卡号
    private String dbcrdCrdno;
    //法人编码
    private String lgperCode;

    //客户号
    private String cusno;
    //合约编号
    private String dbcrdArRefno;
    //卡片生命周期
    private String cardLifeCycle;
}
