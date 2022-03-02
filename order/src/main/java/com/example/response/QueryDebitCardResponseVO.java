package com.example.response;

import com.example.vo.ComposeResultFirstVO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryDebitCardResponseVO {

    List<ComposeResultFirstVO> list;
    //当前页数
    private int pageNo;
    //总条数
    private int totalRecords;
    //总页数
    private int totalPages;

}
