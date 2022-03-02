package com.example.consumer.manager.ibb;

import com.example.constant.DebitCardConstant;
import com.example.consumer.manager.kbb.QueryCustomerUnderCardInfoKBBManager;
import com.example.dao.DebitCardContractDAO;
import com.example.dao.DebitCardDAO;
import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import com.example.vo.ComposeResultFirstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户名下的卡片信息
 * 根据客户号 查询卡片信息
 * 客户号 = 卡片信息中的 客户号
 *  获取合约编号
 */

@Component
public class QueryCustomerUnderCardInfoIBBManager {

    @Autowired
    DebitCardContractDAO debitCardContractDAO;

    @Autowired
    DebitCardDAO debitCardDAO;

    @Autowired
    QueryCustomerUnderCardInfoKBBManager queryCustomerUnderCardInfoKBBManager;

    /**
     * 客户号 =  《卡片表表》客户号   再 查询合约表表
     * @param 
     * @return
     */
    public QueryDebitCardResponseVO queryCustomerUnderDebitCardInfo(QueryDebitCardRequest queryDebitCardRequest){
        String validFlag = queryDebitCardRequest.getValidFlag();
        //根据客户号查询借记卡合约信息
        String cusNo = queryDebitCardRequest.getCusNo();
        DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode();
        List<DebitCardBO> debitCardBOList = debitCardDAO.selectDebitCardByCusNo(cusNo);

        if(!CollectionUtils.isEmpty(debitCardBOList)){
            debitCardBOList  = getDebitCardBOList(debitCardBOList,validFlag);
        }

        //获取卡片中的合约编号用于查询合约表
        List<String> contractNumList = new ArrayList<>(0);
        if(!CollectionUtils.isEmpty(debitCardBOList)){
            debitCardBOList.forEach(s->{
                contractNumList.add(s.getDbcrdArRefno());
            });
        }


        //循环查询合约信息
        List<DebitCardContractBO> debitCardContractBOList = new ArrayList<>(0);
        if(!CollectionUtils.isEmpty(contractNumList)){
            debitCardContractBOList = pageCycleQueryContractBO(contractNumList);
        }

        //组装信息
        List<ComposeResultFirstVO> mapList = queryCustomerUnderCardInfoKBBManager.queryCustomerUnderCardInfo(debitCardBOList, debitCardContractBOList);
        QueryDebitCardResponseVO responseVO = new QueryDebitCardResponseVO();
        responseVO.setList(mapList);
        return responseVO;
    }


    /**
     *
     * @param debitCardBOList
     * @param validFlag
     * @return
     */
    private List<DebitCardBO> getDebitCardBOList(List<DebitCardBO> debitCardBOList,String validFlag){
        //有效
        if("1".equals(validFlag)){
            debitCardBOList.stream().filter(u->!DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode().equals(u.getCardLifeCycle())).collect(Collectors.toList());
        }
        //无效
        if("0".equals(validFlag)){
            debitCardBOList.stream().filter(u->DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode().equals(u.getCardLifeCycle())).collect(Collectors.toList());
        }
        return debitCardBOList;
    }


    /**
     *  卡片集合中循环查询 合约信息，in（合约编号）（合约表的主键）
     * @param list
     * @return
     */
    private List<DebitCardContractBO>  pageCycleQueryContractBO(List<String> list){

        Integer total = list.size();
        Integer size = 1;
        Integer pages = total % size == 0 ? total / size : total / size + 1;
        List<DebitCardContractBO> resultList = new ArrayList<>(total);
        for (int i = 0; i < pages; i++) {
            Integer toIndex = (i + 1) * size > total ? total : (i + 1) * size;
            //分批查询
            List<DebitCardContractBO> orderList = debitCardContractDAO.selectByMutipleContractNo(list.subList(i * size, toIndex));
            //组装结果
            resultList.addAll(orderList);
        }
        return resultList;
    }

}
