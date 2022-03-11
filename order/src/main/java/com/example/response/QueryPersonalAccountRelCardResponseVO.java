package com.example.response;

import com.example.vo.ComposeResultFirstVO;
import com.example.vo.PersonalAccountRelDebitCardVO;
import lombok.Data;

import java.util.List;

@Data
public class QueryPersonalAccountRelCardResponseVO {

    List<PersonalAccountRelDebitCardVO> list;
    //当前页数
    private int pageNo;
    //总条数
    private int totalRecords;
    //总页数
    private int totalPages;

}
