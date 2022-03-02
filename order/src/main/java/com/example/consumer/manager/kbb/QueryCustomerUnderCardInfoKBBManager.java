package com.example.consumer.manager.kbb;

import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.vo.ComposeResultFirstVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueryCustomerUnderCardInfoKBBManager {

    public List<ComposeResultFirstVO> queryCustomerUnderCardInfo(List<DebitCardBO> debitCardBOS, List<DebitCardContractBO> contractBOList) {

        //组合数据
        List<ComposeResultFirstVO> collect = contractBOList
                .stream()
                .flatMap(x -> {
                            if (CollectionUtils.isEmpty(debitCardBOS)) {
                                ComposeResultFirstVO composeResultFirstVO = new ComposeResultFirstVO();
                                composeResultFirstVO.setContractNo(x.getDbcrdArRefno());

                                composeResultFirstVO.setCardLifeCycle("");

                                Stream<ComposeResultFirstVO> composeResultFirstVO1 = Stream.of(composeResultFirstVO);

                                return composeResultFirstVO1;
                            }

                            // 如果list2不包含list1的commonId 或 list2为空，map.put(x)
                            return debitCardBOS
                                    .stream()
                                    .filter(y -> !StringUtils.isEmpty(x.getDbcrdArRefno()) && x.getCusno().equals(y.getCusno())).map(j -> {
                                        ComposeResultFirstVO composeResultFirstVO = new ComposeResultFirstVO();
                                        composeResultFirstVO.setContractNo(x.getDbcrdArRefno());
                                        composeResultFirstVO.setCardLifeCycle(j.getCardLifeCycle());
                                        return composeResultFirstVO;
                                    });
                        }
                ).collect(Collectors.toList());

        //排序
        collect = collect.stream().sorted(Comparator.comparing(ComposeResultFirstVO::getCompanyHoldNo).reversed()).collect(Collectors.toList());

        //分页
        int pageSize = 10;
        int pageNo = 2;
        collect = collect.stream().skip(pageSize * (pageNo - 1)).limit(pageSize).collect(Collectors.toList());
        return collect;

    }

    /**
     *
     * @param
     * @param j
     * @return
     * @throws IllegalAccessException
     */
    private static Map<String, Object> objToMap(Object j) throws IllegalAccessException {
        Map<String, Object> newMap = new HashMap<>();
        // 获取所有字段,public和protected和private,但是不包括父类字段
        Field[] fields = j.getClass().getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
//            newMap.put(field.getName(),field.get(j));
            assembleMap(field,newMap,j);
        }
        return newMap;
    }


    /**
     *
     * @param field
     * @param newMap
     * @param j
     * @throws IllegalAccessException
     */
    private static void assembleMap(Field field, Map<String, Object> newMap, Object j) throws IllegalAccessException {
        if ("".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        } else if ("".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        } else if ("".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        } else if ("".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        }else {

        }
    }
}
