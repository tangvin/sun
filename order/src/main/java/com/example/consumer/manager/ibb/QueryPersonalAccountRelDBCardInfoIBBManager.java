package com.example.consumer.manager.ibb;

import com.example.constant.DebitCardConstant;
import com.example.consumer.manager.kbb.QueryPersonalAccountRelDBCardInfoKBBManager;
import com.example.dao.DebitCardContractDAO;
import com.example.dao.DebitCardDAO;
import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.entity.DebitCardContractSubAccountBO;
import com.example.entity.DebitCardLifeCycleBO;
import com.example.request.requestvo.QueryPersonalAccountRelDBCardRequestVO;
import com.example.response.QueryPersonalAccountRelCardResponseVO;
import com.example.vo.PersonalAccountRelDebitCardVO;
import org.apache.commons.lang.StringUtils;
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
 * 获取合约编号
 */

@Component
public class QueryPersonalAccountRelDBCardInfoIBBManager {

    @Autowired
    DebitCardContractDAO debitCardContractDAO;

    @Autowired
    DebitCardDAO debitCardDAO;

    @Autowired
    QueryPersonalAccountRelDBCardInfoKBBManager queryPersonalAccountRelDBCardInfoKBBManager;

    /**
     * 客户号 =  再 查询合约表表
     *
     * @param
     * @return
     */
    public QueryPersonalAccountRelCardResponseVO queryPersonalAccountRelDBCardInfo(QueryPersonalAccountRelDBCardRequestVO requestVO) {

        List<DebitCardBO> debitCardBOList = requestVO.getDebitCardBOList();
        List<DebitCardContractBO> debitCardContractBOList = requestVO.getDebitCardContractBOList();
        List<DebitCardContractSubAccountBO> subAccountBOList = requestVO.getDebitCardContractSubAccountBOList();
        List<DebitCardLifeCycleBO> debitCardLifeCycleBOList = requestVO.getDebitCardLifeCycleBOList();

        //匹配数据分页
        List<PersonalAccountRelDebitCardVO> mapList = new ArrayList<>(0);
        if (!CollectionUtils.isEmpty(debitCardBOList)) {
            mapList = queryPersonalAccountRelDBCardInfoKBBManager.queryPersonalAccountRelDBCardInfo(debitCardBOList, debitCardContractBOList, subAccountBOList, debitCardLifeCycleBOList);
        }
        //组装返回数据
        return returnResponse(mapList, requestVO);
    }


    /**
     * @param debitCardBOList
     * @param validFlag
     * @return
     */
    private List<DebitCardBO> getDebitCardBOList(List<DebitCardBO> debitCardBOList, String validFlag) {
        //有效
        if ("".equals(validFlag)) {
            debitCardBOList.stream().filter(u -> !DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode().equals(u.getCardLifeCycle())).collect(Collectors.toList());
        }
        //无效
        if ("0".equals(validFlag)) {
            debitCardBOList.stream().filter(u -> DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode().equals(u.getCardLifeCycle())).collect(Collectors.toList());
        }
        return debitCardBOList;
    }


    /**
     * 卡片集合中循环查询 合约信息，in（合约编号）（合约表的主键）
     *
     * @param list
     * @return
     */
    private List<DebitCardContractBO> pageCycleQueryContractBO(List<String> list) {

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

    private QueryPersonalAccountRelCardResponseVO returnResponse(List<PersonalAccountRelDebitCardVO> mapList, QueryPersonalAccountRelDBCardRequestVO requestVO) {
        QueryPersonalAccountRelCardResponseVO responseVO = new QueryPersonalAccountRelCardResponseVO();
        responseVO.setList(mapList);
        int pageNo = 1;
        if (!StringUtils.isBlank(requestVO.getPageNo())) {
            pageNo = Integer.parseInt(requestVO.getPageNo());
        }
        int pageSize = 10;
        if (!StringUtils.isBlank(requestVO.getPageNo())) {
            pageSize = Integer.parseInt(requestVO.getPageSize());
        }
        responseVO.setPageNo(pageNo);
        responseVO.setPageSize(pageSize);

        int totalRecords = 0;
        if (!CollectionUtils.isEmpty(mapList)) {
            totalRecords = mapList.size();
        }
        responseVO.setTotalRecords(totalRecords);
        int totalPages = totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1;
        responseVO.setTotalPages(totalPages);
        return responseVO;
    }

}
