package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryDebitCardVo {

    //客户号
    private String cusNo;
    //有效标志
    private String validFlag;

}
