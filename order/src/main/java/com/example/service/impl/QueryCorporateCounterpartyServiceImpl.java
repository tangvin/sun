package com.example.service.impl;

import com.example.consumer.manager.ibb.QueryCorporateCounterpartyIBBManager;
import com.example.request.OneRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/24   18:41
 * @version: 1.0
 * @modified:
 */
public class QueryCorporateCounterpartyServiceImpl {

    @Autowired
    QueryCorporateCounterpartyIBBManager queryCorporateCounterpartyIBBManager;

    public void test1(OneRequest request){
        queryCorporateCounterpartyIBBManager.test1(request);
    }

}
