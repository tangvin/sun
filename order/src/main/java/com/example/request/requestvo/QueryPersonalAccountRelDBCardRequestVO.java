package com.example.request.requestvo;

import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.entity.DebitCardContractSubAccountBO;
import com.example.entity.DebitCardLifeCycleBO;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2022/03/11   19:11
 * @version: 1.0
 * @modified:
 */
@Data
public class QueryPersonalAccountRelDBCardRequestVO {

    //存款合约编号
    private String depositNum;


    private List<DebitCardBO> debitCardBOList;
    private List<DebitCardContractBO> debitCardContractBOList;
    private List<DebitCardContractSubAccountBO> debitCardContractSubAccountBOList;
    private List<DebitCardLifeCycleBO> debitCardLifeCycleBOList;
}
