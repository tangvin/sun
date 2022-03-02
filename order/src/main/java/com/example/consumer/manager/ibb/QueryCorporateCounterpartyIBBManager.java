package com.example.consumer.manager.ibb;

import com.example.request.OneRequest;
import com.example.request.ThreeRequest;
import com.example.request.TwoRequest;
import com.example.response.QueryCorporateCounterpartyResponseVO;
import org.junit.Test;
import org.springframework.stereotype.Component;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/24   18:41
 * @version: 1.0
 * @modified:
 */
@Component
public class QueryCorporateCounterpartyIBBManager {


    public QueryCorporateCounterpartyResponseVO test1(OneRequest request){

        String pageSize = request.getPageSize();

        return null;
    }

    public QueryCorporateCounterpartyResponseVO test2(TwoRequest request){
        String pageSize = request.getPageSize();
        return null;
    }

    public QueryCorporateCounterpartyResponseVO test3(ThreeRequest request){
        String pageSize = request.getPageSize();
        return null;
    }

    public QueryCorporateCounterpartyResponseVO test4(Object obj){
        OneRequest oneRequest = new OneRequest();
        AbstractQueryCorporateCounterpartyIBBManager manager = new AbstractQueryCorporateCounterpartyIBBManager(oneRequest);
        Object strategyMode = manager.getStrategyMode();
        System.out.println("strategyMode"+strategyMode);

        return null;
    }

    @Test
    public void test1(){
        test4(null);
    }
}
