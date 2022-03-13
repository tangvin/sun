package com.example.consumer.manager.kbb;

import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.entity.DebitCardContractSubAccountBO;
import com.example.entity.DebitCardLifeCycleBO;
import com.example.vo.PersonalAccountRelDebitCardVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueryPersonalAccountRelDBCardInfoKBBManager {

    public List<PersonalAccountRelDebitCardVO> queryPersonalAccountRelDBCardInfo(List<DebitCardBO> debitCardBOList, List<DebitCardContractBO> contractBOList, List<DebitCardContractSubAccountBO> subAccountBOList, List<DebitCardLifeCycleBO> debitCardLifeCycleBOList) {

        //组合数据
        List<PersonalAccountRelDebitCardVO> collect = debitCardBOList.stream().flatMap(x -> {
                            return contractBOList.stream().filter(y -> !StringUtils.isEmpty(x.getDbcrdArRefno()) && x.getDbcrdArRefno().equals(y.getDbcrdArRefno())).flatMap(j -> {
                               return subAccountBOList.stream().filter(z ->!StringUtils.isEmpty(j.getDbcrdArRefno())  && j.getDbcrdArRefno().equals(z.getDbcrdArRefno())).map(s->{
                                    PersonalAccountRelDebitCardVO personalVO1 = new PersonalAccountRelDebitCardVO();
                                    //合约表和分户表数据组装
                                   personalVO1.setCurrentIdentification(s.getCurrentIdentification());//定活期标志
                                   personalVO1.setOneHousehold(s.getOneHousehold());//一户通字
                                   personalVO1.setCompanyHoldNo(j.getCscdCrdhlRefno());//单位持卡人编号
                                   personalVO1.setAlsalPdRedno(j.getAlsalPdRedno());//可售产品编码
                                    return personalVO1;
                               });
                            }).map(p->{
                                PersonalAccountRelDebitCardVO personalVO2 = new PersonalAccountRelDebitCardVO();
                                personalVO2.setCurrentIdentification(p.getCurrentIdentification());//定活期标志
                                personalVO2.setOneHousehold(p.getOneHousehold());//一户通字
                                personalVO2.setCompanyHoldNo(p.getCompanyHoldNo());//单位持卡人编号
                                personalVO2.setAlsalPdRedno(p.getAlsalPdRedno());//可售产品编码

                                personalVO2.setDbcrdArRefno(x.getDbcrdArRefno());//合约编号
                                personalVO2.setCardLifeCycle(x.getCardLifeCycle());//卡片生命周期
                                personalVO2.setDbcrdCrdno(x.getDbcrdCrdno());//借记卡卡号
                                return personalVO2;
                            });
                        }
                ).collect(Collectors.toList());


        List<PersonalAccountRelDebitCardVO> retList = collect.stream().distinct().collect(Collectors.toList());

        List<PersonalAccountRelDebitCardVO> resultList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(debitCardLifeCycleBOList)){
             resultList = retList.stream().flatMap(m->{
                return debitCardLifeCycleBOList.stream().filter(i-> !StringUtils.isEmpty(m.getDbcrdCrdno()) && m.getDbcrdCrdno().equals(i.getDbcrdCrdno())).flatMap(j->{
                    PersonalAccountRelDebitCardVO personalVO3 = new PersonalAccountRelDebitCardVO();
                    personalVO3.setCurrentIdentification(m.getCurrentIdentification());//定活期标志
                    personalVO3.setOneHousehold(m.getOneHousehold());//一户通字
                    personalVO3.setCompanyHoldNo(m.getCompanyHoldNo());//单位持卡人编号
                    personalVO3.setAlsalPdRedno(m.getAlsalPdRedno());//可售产品编码
                    personalVO3.setDbcrdArRefno(m.getDbcrdArRefno());//合约编号
                    personalVO3.setCardLifeCycle(m.getCardLifeCycle());//卡片生命周期
                    personalVO3.setDbcrdCrdno(m.getDbcrdCrdno());//借记卡卡号

                    personalVO3.setReceiveDate(j.getReceiveDate());//领卡日期
                    return Stream.of(personalVO3);
                });
            }).collect(Collectors.toList());
        }

        //排序
//        collect = collect.stream().sorted(Comparator.comparing(ComposeResultFirstVO::getCompanyHoldNo).reversed()).collect(Collectors.toList());

        //分页
//        int pageSize = 10;
//        int pageNo = 2;
//        collect = collect.stream().skip(pageSize * (pageNo - 1)).limit(pageSize).collect(Collectors.toList());
        return resultList;

    }


}
