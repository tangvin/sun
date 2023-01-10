package com.example.vo;

import com.example.entity.po.TransBillInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2023/01/08   19:56
 * @version: 1.0
 * @modified:
 */
@Setter
@Getter
@ToString
public class OverdueBillVO {


    //账单类型
    private String billType;
    //账期编号
    private String billNum;
    //逾期金额
    private BigDecimal billTotalAmt;
    //逾期罚息
    private BigDecimal billTotalPushInt;

    private List<TransBillInfo> overdueDetailList;

}
