package com.example.service;

import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import com.example.vo.QueryDebitCardVo;

public interface QueryCustomerUnderCardInfoService {

    /**
     * 查询客户名下的借记卡
     * @param queryDebitCardRequest
     * @return
     */
    QueryDebitCardResponseVO queryCustomerUnderDebitCardInfo(QueryDebitCardRequest queryDebitCardRequest);

    /**
     * 查询客户持有的单位结算卡
     * @param queryDebitCardRequest
     * @return
     */
    QueryDebitCardResponseVO queryCustomerHoldSettlementCard(QueryDebitCardRequest queryDebitCardRequest);
}
