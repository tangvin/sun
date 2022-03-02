package com.example.service.impl;

import com.example.consumer.manager.tbb.QueryCustomerUnderCardInfoTechManager;
import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import com.example.service.QueryCustomerUnderCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryCustomerUnderCardInfoServiceImpl implements QueryCustomerUnderCardInfoService {

    @Autowired
    QueryCustomerUnderCardInfoTechManager queryCustomerUnderCardInfoTechManager;

    /**
     * 查询客户名下的借记卡
     * @param queryDebitCardRequest
     * @return
     */
    public QueryDebitCardResponseVO queryCustomerUnderDebitCardInfo(QueryDebitCardRequest queryDebitCardRequest){

        QueryDebitCardResponseVO debitCardResponseVO = queryCustomerUnderCardInfoTechManager.queryCustomerUnderDebitCardInfo(queryDebitCardRequest);

        return debitCardResponseVO;
    }

    /**
     * 查询客户持有的单位结算卡
     * @param queryDebitCardRequest
     * @return
     */
    public QueryDebitCardResponseVO queryCustomerHoldSettlementCard(QueryDebitCardRequest queryDebitCardRequest){
        QueryDebitCardResponseVO debitCardResponseVO = queryCustomerUnderCardInfoTechManager.queryCustomeHoldSettmentCard(queryDebitCardRequest);
        return debitCardResponseVO;
    }
}
