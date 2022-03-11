package com.example.consumer.manager.kbb;

import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.entity.DebitCardContractSubAccountBO;
import com.example.entity.DebitCardLifeCycleBO;
import com.example.vo.PersonalAccountRelDebitCardVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueryPersonalAccountRelDBCardInfoKBBManager {

    public List<PersonalAccountRelDebitCardVO> queryPersonalAccountRelDBCardInfo(List<DebitCardBO> debitCardBOS, List<DebitCardContractBO> contractBOList, List<DebitCardContractSubAccountBO> subAccountBOList, List<DebitCardLifeCycleBO> debitCardLifeCycleBOList) {

        //组合数据
        List<PersonalAccountRelDebitCardVO> collect = contractBOList
                .stream()
                .flatMap(x -> {
                            if (CollectionUtils.isEmpty(debitCardBOS)) {
                                PersonalAccountRelDebitCardVO personalAccountRelDebitCardVO = new PersonalAccountRelDebitCardVO();
                                personalAccountRelDebitCardVO.setContractNo(x.getDbcrdArRefno());
                                personalAccountRelDebitCardVO.setCardLifeCycle("");
                                Stream<PersonalAccountRelDebitCardVO> vo = Stream.of(personalAccountRelDebitCardVO);
                                return vo;
                            }

                            return debitCardBOS.stream().filter(y -> !StringUtils.isEmpty(x.getDbcrdArRefno()) && x.getCusno().equals(y.getCusno())).map(j -> {
                                PersonalAccountRelDebitCardVO personalAccountRelDebitCardVO = new PersonalAccountRelDebitCardVO();
                                personalAccountRelDebitCardVO.setContractNo(x.getDbcrdArRefno());
                                personalAccountRelDebitCardVO.setCardLifeCycle(j.getCardLifeCycle());
                                return personalAccountRelDebitCardVO;
                            });
                        }
                ).collect(Collectors.toList());

        //排序
//        collect = collect.stream().sorted(Comparator.comparing(ComposeResultFirstVO::getCompanyHoldNo).reversed()).collect(Collectors.toList());

        //分页
        int pageSize = 10;
        int pageNo = 2;
        collect = collect.stream().skip(pageSize * (pageNo - 1)).limit(pageSize).collect(Collectors.toList());
        return collect;

    }


}
