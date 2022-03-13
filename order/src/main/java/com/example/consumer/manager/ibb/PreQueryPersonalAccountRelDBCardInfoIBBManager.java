package com.example.consumer.manager.ibb;

import com.example.dao.DebitCardContractDAO;
import com.example.dao.DebitCardContractSubAccountDAO;
import com.example.dao.DebitCardDAO;
import com.example.dao.DebitCardLifeCycleDAO;
import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.entity.DebitCardContractSubAccountBO;
import com.example.entity.DebitCardLifeCycleBO;
import com.example.request.requestvo.QueryPersonalAccountRelDBCardRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2022/03/11   19:06
 * @version: 1.0
 * @modified:
 */
@Component
public class PreQueryPersonalAccountRelDBCardInfoIBBManager {

    @Autowired
    DebitCardContractSubAccountDAO debitCardContractSubAccountDAO;
    @Autowired
    DebitCardDAO debitCardDAO;
    @Autowired
    DebitCardContractDAO debitCardContractDAO;
    @Autowired
    DebitCardLifeCycleDAO debitCardLifeCycleDAO;


    public QueryPersonalAccountRelDBCardRequestVO preQuery(QueryPersonalAccountRelDBCardRequestVO requestVO) throws Exception {
        String depositNum = requestVO.getDepositNum();
        //分户信息存在一定会有对应的卡片和合约信息
        List<DebitCardContractSubAccountBO> subAccountBOList = debitCardContractSubAccountDAO.selectListByPage(depositNum,10,1);
        requestVO.setDebitCardContractSubAccountBOList(subAccountBOList);
        if (!CollectionUtils.isEmpty(subAccountBOList)) {
            //获取借记卡合约编号
            List<String> list = subAccountBOList.stream().map(s -> s.getDbcrdArRefno()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(list)) {
                //查询卡片信息
                List<DebitCardBO> debitCardBOList = debitCardDAO.selectDebitCardByMutipleContractId(list);
                if (CollectionUtils.isEmpty(debitCardBOList)) {
                    throw new Exception("卡片信息为空");
                }

                //查询合约信息
                List<DebitCardContractBO> debitCardContractBOList = debitCardContractDAO.selectByMutipleContractNo(list);
                if (CollectionUtils.isEmpty(debitCardContractBOList)) {
                    throw new Exception("合约信息为空");
                }

                //查询生命周期表
                List<DebitCardLifeCycleBO> debitCardLifeCycleBOList = debitCardLifeCycleDAO.selectList(list);

                requestVO.setDebitCardBOList(debitCardBOList);
                requestVO.setDebitCardContractBOList(debitCardContractBOList);
                requestVO.setDebitCardLifeCycleBOList(debitCardLifeCycleBOList);
            }
        }
        return requestVO;
    }
}
