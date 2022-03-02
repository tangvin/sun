package com.example.order;

import com.example.order.bean.Order;
import com.example.order.bean.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssembleDataTest {

    @Test
    public void contextLoads() {
        //根据  commonId;查询
        List<Order> list1 = new ArrayList<>();
        list1.add(new Order().setId(11).setName("张1三").setAddress("内1蒙古").setCommonId("55"));
        list1.add(new Order().setId(22).setName("张2三").setAddress("内2蒙古").setCommonId("6"));

        //根据  commonId 查询
        List<User> list2 = new ArrayList<>();
        list2.add(new User().setCommonId("8").setPhone("苹果").setAge(100));
        list2.add(new User().setCommonId("6").setPhone("苹2果").setAge(100));

        List<Map<String, Object>> maps = asseData(list1, list2);
        System.out.println(maps);
    }


    public List<Map<String, Object>> asseData(List<Order> list1, List<User> list2) {
        if (CollectionUtils.isEmpty(list1)) {
            return new ArrayList<>(0);
        }
        List<Map<String, Object>> collect = list1.stream().flatMap(x -> {
                    // 如果list2不包含list1的commonId 或 list2为空，map.put(x)
                    if (CollectionUtils.isEmpty(list2) || list2.stream().noneMatch(o -> !StringUtils.isEmpty(x.getCommonId()) && x.getCommonId().equals(o.getCommonId()))) {
                        // list1对象属性
                        Map<String, Object> map = new HashMap<>();
                        try {
                            map.putAll(objToMap(new HashMap<>(), x)); //list1对象属性
                            map.putAll(objToMap(map, new User())); //list1对象属性
                        } catch (IllegalAccessException e) {
                            System.out.println("log4j打印错误");
                        }
                        return Stream.of(map);
                    } else {
                        return list2.stream().filter(y -> !StringUtils.isEmpty(x.getCommonId()) && x.getCommonId().equals(y.getCommonId())).map(j -> {
                            Map<String, Object> map = new HashMap<>();
                            try {
                                map.putAll(objToMap(new HashMap<>(), x)); //list1对象属性
                                map.putAll(objToMap(map, j)); //list2对象属性
                            } catch (IllegalAccessException e) {
                                System.out.println("log4j打印错误");
                            }
                            return map;
                        });
                    }
                }
        ).collect(Collectors.toList());
        return collect;
    }

    private static Map<String, Object> objToMap(Map<String, Object> map, Object j) throws IllegalAccessException {
        Map<String, Object> newMap = new HashMap<>();
        // 获取所有字段,public和protected和private,但是不包括父类字段
        Field[] fieldsj = j.getClass().getDeclaredFields();
        for (Field field : fieldsj) {
            ReflectionUtils.makeAccessible(field);
            // 如map中有包含属性不重新赋值
            if (!map.containsKey(field.getName())) {
                newMap.put(field.getName(), field.get(j));
            }
        }
        return newMap;
    }

}
