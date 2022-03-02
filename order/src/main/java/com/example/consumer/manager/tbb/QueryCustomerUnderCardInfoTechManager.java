package com.example.consumer.manager.tbb;

import com.example.consumer.manager.ibb.QueryCustomerHoldSettlementCardInfoIBBManager;
import com.example.consumer.manager.ibb.QueryCustomerUnderCardInfoIBBManager;
import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryCustomerUnderCardInfoTechManager {

    @Autowired
    QueryCustomerUnderCardInfoIBBManager queryCustomerUnderCardInfoIBBManager;
    @Autowired
    QueryCustomerHoldSettlementCardInfoIBBManager queryCustomerHoldSettlementCardInfoIBBManager;

    /**
     * 查询客户名下的借记卡
     * @param queryDebitCardRequest
     * @return
     */
    public QueryDebitCardResponseVO queryCustomerUnderDebitCardInfo(QueryDebitCardRequest queryDebitCardRequest){
        QueryDebitCardResponseVO responseVO = queryCustomerUnderCardInfoIBBManager.queryCustomerUnderDebitCardInfo(queryDebitCardRequest);
        return responseVO;
    }

    /**
     * 查询客户持有的单位结算卡
     * @param queryDebitCardRequest
     * @return
     */
    public QueryDebitCardResponseVO queryCustomeHoldSettmentCard(QueryDebitCardRequest queryDebitCardRequest){
        QueryDebitCardResponseVO responseVO = queryCustomerHoldSettlementCardInfoIBBManager.queryCustomerUnderDebitCardInfo(queryDebitCardRequest);
        return responseVO;
    }
}
