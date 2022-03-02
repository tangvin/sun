package com.example.consumer.manager.ibb;

import com.example.request.OneRequest;
import com.example.request.ThreeRequest;
import com.example.request.TwoRequest;
import com.example.response.QueryCorporateCounterpartyResponseVO;

/**
 * @className: 策略类
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/24   22:37
 * @version: 1.0
 * @modified:
 */
public class AbstractQueryCorporateCounterpartyIBBManager {

    StrategyMode strategyMode;

    <T> AbstractQueryCorporateCounterpartyIBBManager(T t) {
        this.strategyMode = (StrategyMode) t;
    }

    Object getStrategyMode() {
        return strategyMode.doSubmit();
    }


}
