package com.example.gateway;

import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import com.example.service.QueryCustomerUnderCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class QueryCustomerUnderCardInfoGateway {

    @Autowired
    QueryCustomerUnderCardInfoService queryCustomerUnderCardInfoService;

    /**
     * 查询客户名下的借记卡
     * @param QueryDebitCardRequest
     * @return
     */
    @PostMapping("/queryCustomerUnderDebitCardInfo")
    @ResponseBody
    public QueryDebitCardResponseVO queryCustomerUnderDebitCardInfo(@RequestBody QueryDebitCardRequest QueryDebitCardRequest){

        QueryDebitCardResponseVO responseVO = queryCustomerUnderCardInfoService.queryCustomerUnderDebitCardInfo(QueryDebitCardRequest);
        return responseVO;
    }

    /**
     * 查询客户持有的单位结算卡
     * @param QueryDebitCardRequest
     * @return
     */
    @PostMapping("/queryCustomerHoldSettlementCard")
    @ResponseBody
    public QueryDebitCardResponseVO queryCustomerHoldSettlementCard(@RequestBody QueryDebitCardRequest QueryDebitCardRequest){
        QueryDebitCardResponseVO responseVO = queryCustomerUnderCardInfoService.queryCustomerHoldSettlementCard(QueryDebitCardRequest);
        return responseVO;
    }

}
