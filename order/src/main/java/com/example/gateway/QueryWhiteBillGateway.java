package com.example.gateway;

import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import com.example.service.QueryWhiteBillService;
import com.example.vo.OverdueBillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2023/01/08   23:25
 * @version: 1.0
 * @modified:
 */
@RestController
@RequestMapping("/queryWhiteBillGateway")
public class QueryWhiteBillGateway {


    @Autowired
    QueryWhiteBillService queryWhiteBillService;

    /**
     * 逾期账单查询
     * @param userId
     * @param billType
     * @return
     */
    @GetMapping("/queryOverdueBill")
    @ResponseBody
    public List<OverdueBillVO> queryOverdueBill(@RequestParam String userId,@RequestParam String billType){
        List<OverdueBillVO> overdueBillVOList = queryWhiteBillService.queryOverdueBillList(userId, billType);
        return overdueBillVOList;
    }



}
