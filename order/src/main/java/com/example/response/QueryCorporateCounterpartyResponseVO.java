package com.example.response;

import com.example.vo.CounterpartyResultVO;

import java.util.List;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/24   18:43
 * @version: 1.0
 * @modified:
 */
public class QueryCorporateCounterpartyResponseVO {

    List<CounterpartyResultVO> list;
    //当前页数
    private int pageNo;
    //总条数
    private int totalRecords;
    //总页数
    private int totalPages;
}
