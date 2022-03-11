package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DebitCardContractSubAccountBO {
    //合约编号
    private String dbcrdArRefno;
    //存款合约编号
    private String depositNum;
    //一户通字
    private String tongZi;
}
