package com.example.order;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.internal.util.unsafe.MpmcArrayQueue;

import java.awt.image.RescaleOp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {

    @Test
    public void contextLoads() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.forEach(res -> {
            System.out.println(res.contains("2"));
        });
        HashMap hashMap = new HashMap<String, Object>();
        hashMap.put("name", "zhansan");
        hashMap.put("age", "20");
        hashMap.put("gender", "man");
        hashMap.put("birthday", "1999-10-01");
        strings.stream().filter(s -> {
            System.out.println(s.indexOf("1"));
            return false;
        });
        hashMap.entrySet().forEach(res -> System.out.println("key_" + res.toString()));

        hashMap.forEach((key, value) -> {
            System.out.println("key ==" + key + "  value=" + value);
        });

    }


    @Test
    public void test1() {
        String[] str = {"1", "2", "3"};
        String[] str1 = {"1", "2", "3", "4"};
        String[] strings = new String[str.length];
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("str", str);
        hashMap.put("str1", str1);
        hashMap.forEach((k, v) -> {
            if ("str".equals(k)) {
                System.out.println("k===" + k);
                System.out.println("v===" + v.toString());
            }
        });
//       hashMap.entrySet().forEach();
        Arrays.asList(str).stream().forEach(a -> System.out.println("a  ==" + a));
        String s = "[{name:\"tang\", age\":\"10\",gender:\"man\"}]";
        String s1 = "[{name:\"tang\", age\":\"10\",gender:\"man\"},{name:\"tang\", age\":\"10\",gender:\"man\"}]";
        String joStr = "{name:\"张三\",age:\"20\"}";
        String joStr555 = "{'name':'张三','age':'20'}";
        String joStr1 = "[{\"name\":\"张三\",\"age\":\"20\"}]";
        String str444 = "[{'columnId':5,'columnName':'人文历史'},{'columnId':2,'columnName':'商业视野'}]";
        String aa = "1";

//        List<Map> mapList = JSONArray.parseArray(str,Map.class);
//
//        mapList.forEach(map1 -> { map1.forEach((k,v) -> { System.out.println(v); }); });


//        JSONObject jsonObject = JSONObject.parseObject(joStr1);
        List<Map> maps = JSONObject.parseArray(str444, Map.class);

        List<Map<String, Object>> a = null;
        System.out.println("maps===" + maps);
        maps.forEach(map -> {
            map.forEach((k, v) -> System.out.println("k==" + k + "    v==" + v));
        });


    }

}
