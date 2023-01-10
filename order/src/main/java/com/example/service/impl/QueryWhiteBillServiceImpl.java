package com.example.service.impl;

import com.example.dao.TransBillInfoDAO;
import com.example.entity.po.TransBillInfo;
import com.example.service.QueryWhiteBillService;
import com.example.vo.OverdueBillVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2023/01/08   19:55
 * @version: 1.0
 * @modified:
 */
@Service
@Slf4j
public class QueryWhiteBillServiceImpl implements QueryWhiteBillService {

    @Autowired
    TransBillInfoDAO transBillInfoDAO;

    @Override
    public List<OverdueBillVO> queryOverdueBillList(String userId, String billType) {
        List<Map<String, Object>> mapList = transBillInfoDAO.selectOverdueBillGroupByBillNum(userId, billType);

        List<OverdueBillVO> monthOverdueBillVOList = new ArrayList<>(0);

        if(!CollectionUtils.isEmpty(mapList)){
            mapList.forEach(s->{
                OverdueBillVO overdueBillVO = new OverdueBillVO();
                String billNum = (String)s.get("billNum");
                overdueBillVO.setBillNum(billNum);
                BigDecimal totalBalance = (BigDecimal)s.get("totalBalance");
                overdueBillVO.setBillTotalPushInt(totalBalance);
                BigDecimal totalBillAmt = (BigDecimal)s.get("totalBillAmt");
                overdueBillVO.setBillTotalAmt(totalBillAmt);
                monthOverdueBillVOList.add(overdueBillVO);
                System.out.println("billNum："+billNum + ", totalBalance:"+totalBalance+", totalBillAmt:"+totalBillAmt);

                //每月账期的逾期明细
                List<TransBillInfo> transBillInfos = transBillInfoDAO.selectOverdueBillDetail(userId, billType, "202301", "5");

                List<TransBillInfo> overdueDetailList = new ArrayList<>(0);
                if(!CollectionUtils.isEmpty(transBillInfos)){
                    transBillInfos.forEach(y->{
                        TransBillInfo billInfo = new TransBillInfo();
                        billInfo.setBillNum(y.getBillNum());
                        billInfo.setBillType(y.getBillType());
                        billInfo.setBillAmt(y.getBillAmt());
                        billInfo.setBalance(y.getBalance());
                        billInfo.setRepayState(y.getRepayState());
                        overdueDetailList.add(billInfo);
                    });
                }
                overdueBillVO.setOverdueDetailList(overdueDetailList);
                log.info("overdueDetailList==============:{}",overdueDetailList);

            });
        }
        log.info("mapList==============:{}",mapList);
        log.info("monthOverdueBillVOList==============:{}",monthOverdueBillVOList);

        return monthOverdueBillVOList;
    }
}
