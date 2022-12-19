package com.example.order;

import com.example.vo.PersonalAccountRelDebitCardVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2022/03/23   21:50
 * @version: 1.0
 * @modified:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapAndBoTest {

    @Test
    public void contextLoads() {
        //根据  commonId;查询
        List<PersonalAccountRelDebitCardVO> voList = new ArrayList<>();
        PersonalAccountRelDebitCardVO vo1 = new PersonalAccountRelDebitCardVO();
        vo1.setDbcrdArRefno("111");
        vo1.setCompanyHoldNo("111");

        PersonalAccountRelDebitCardVO vo2 = new PersonalAccountRelDebitCardVO();
        vo2.setDbcrdArRefno("222");
        vo2.setCompanyHoldNo("222");
        voList.add(vo1);
        voList.add(vo2);

        HashMap<String, String> map = new HashMap<>();
        map.put("111","1的名称");
        map.put("222","2的名称");
        List<PersonalAccountRelDebitCardVO> asseData = asseData2(voList, map);
        System.out.println("---------personalAccountRelDebitCardVO--------"+asseData);

    }

    public List<PersonalAccountRelDebitCardVO> asseData(List<PersonalAccountRelDebitCardVO> voList, Map<String, String> map) {
        List<PersonalAccountRelDebitCardVO> collect = voList.stream().flatMap(m -> {
            return map.keySet().stream().filter(n -> !StringUtils.isEmpty(m.getCompanyHoldNo()) && m.getCompanyHoldNo().equals(n)).flatMap(k -> {
                PersonalAccountRelDebitCardVO vo1 = new PersonalAccountRelDebitCardVO();
                vo1.setCompanyHoldNo(k);
                vo1.setAlsalPdRedno("6666");
                return Stream.of(vo1);
            });
        }).collect(Collectors.toList());
        return collect;
    }

    public List<PersonalAccountRelDebitCardVO> asseData2(List<PersonalAccountRelDebitCardVO> voList, Map<String, String> map) {
        voList.forEach(i-> {
            String name = map.get(i.getCompanyHoldNo());
            System.out.println("name====="+name);
            i.setCompanyHoldName(name);
            System.out.println("i========="+i);
        });
        return voList;
    }


}
