package com.example.entity.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2023/01/08   19:44
 * @version: 1.0
 * @modified:
 */
@Setter
@Getter
@ToString
public class TransBillInfo {

    private Long id;
    private String billId;
    private String superBillId;
    private String billNum;
    private String userId;
    private String balance;
    private String billAmt;
    private String billType;
    private String repayState;
    private String billState;

}
