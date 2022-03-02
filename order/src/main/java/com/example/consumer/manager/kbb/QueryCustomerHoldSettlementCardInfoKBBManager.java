package com.example.consumer.manager.kbb;

import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.vo.ComposeResultFirstVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueryCustomerHoldSettlementCardInfoKBBManager {

    /**
     *
     * @param debitCardBOS
     * @param contractBOList
     * @return
     */
    public List<ComposeResultFirstVO> queryCustomerUnderCardInfo(List<DebitCardBO> debitCardBOS, List<DebitCardContractBO> contractBOList) {

        List<ComposeResultFirstVO> collect = contractBOList.stream().flatMap(x -> {
                    // 如果list2不包含list1的commonId 或 list2为空，map.put(x)
                    if (CollectionUtils.isEmpty(debitCardBOS) || (!debitCardBOS.isEmpty() && debitCardBOS.stream().noneMatch(i -> !StringUtils.isEmpty(x.getDbcrdArRefno()) && x.getDbcrdArRefno().equals(i.getDbcrdArRefno())))) {
                        return whenCardBOIsNull(x);
                    }
                    //根据合约编号合并数据
                    return debitCardBOS.stream().filter(y -> !StringUtils.isEmpty(x.getDbcrdArRefno()) && x.getDbcrdArRefno().equals(y.getDbcrdArRefno())).map(j ->
                                    whenCardBOIsNotNull(x, j)
                            );
                }
        ).collect(Collectors.toList());

        //排序-改变原来的集合
        collect.sort(Comparator.comparing(ComposeResultFirstVO::getIssueDate).reversed());
//        collect.stream().sorted(Comparator.comparing(ComposeResultFirstVO::getIssueDate).reversed());
        //分页
        int pageSize = 10;
        int pageNo = 1;
        collect = collect.stream().skip(pageSize * (pageNo - 1)).limit(pageSize).collect(Collectors.toList());
        return collect;
    }


    /**
     * @description:
     * @author: Bruce_T
     * @param  * @param null
     * @return {@link null}
     * @data: 2022/2/14 11:29
     * @modified:
     */
    private Stream<ComposeResultFirstVO> whenCardBOIsNull(DebitCardContractBO x){
        ComposeResultFirstVO composeResultFirstVO = new ComposeResultFirstVO();
        composeResultFirstVO.setContractNo(x.getDbcrdArRefno());
        composeResultFirstVO.setCusNo(x.getCusno());
        composeResultFirstVO.setCompanyHoldNo(x.getCscdCrdhlRefno());
        composeResultFirstVO.setCardLifeCycle("");
        composeResultFirstVO.setIssueDate(x.getIssueDate());
        composeResultFirstVO.setSaleableProductCode(x.getLgperCode());
        return Stream.of(composeResultFirstVO);
    }

    /**
     * @description:
     * @author: Bruce_T
     * @param  * @param null
     * @return {@link null}
     * @data: 2022/2/14 11:29
     * @modified:
     */
    private ComposeResultFirstVO whenCardBOIsNotNull(DebitCardContractBO x,DebitCardBO j){
        ComposeResultFirstVO composeResultFirstVO = new ComposeResultFirstVO();
        composeResultFirstVO.setContractNo(x.getDbcrdArRefno());
        composeResultFirstVO.setCusNo(x.getCusno());
        composeResultFirstVO.setCompanyHoldNo(x.getCscdCrdhlRefno());
        composeResultFirstVO.setCardLifeCycle(j.getCardLifeCycle());
        composeResultFirstVO.setIssueDate(x.getIssueDate());
        composeResultFirstVO.setSaleableProductCode(x.getLgperCode());
        return composeResultFirstVO;
    }



}
