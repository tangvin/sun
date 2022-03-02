package com.example.order.api;

import com.example.order.bean.User;
import com.example.order.feign.UserMicroServiceFeignClient;
import com.example.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/5  11:25
 * @Version: 1.0
 * @Modified: By:
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserMicroServiceFeignClient userMicroServiceFeignClient;
    @Autowired
    JdbcTemplate jdbcTemplate;
//    @Autowired
//    RedisTemplate<String,Object> redisTemplate;


//    @RequestMapping("/getOrderById")
//    public Object getOrderById(String id) {
//        logger.debug("param id : {}", id);
//        Order order = userService.findById(Integer.parseInt(id));
//        Object o = JSON.toJSON(order);
//        Object oSTR = JSON.toJSONString(order);
//        System.out.println("JSON.toJSON(order)==" + JSON.toJSON(order));
//        System.out.println("JSON.toJSONString(order)==" + JSON.toJSONString(order));
//        return o;
//    }
//
//    @RequestMapping("/getByIdEureka")
//    public Object getUserByIdEureka(String userId) {
////        String url = "http://192.168.3.5:8081/user/getById?id=" + userId;
//        String url = "http://microservice-user/user/getById?id=" + userId;
//        Object result = restTemplate.getForEntity(url, Object.class);
//        return result;
//    }


    @RequestMapping("/getByIdFeign")
    public Object getUserByIdFeign(String userId) {
        Object result = userMicroServiceFeignClient.getUserById(userId);
        return result;
    }

    @RequestMapping("/testUserPost")
    public Object testUserPost(User user) {
        User users = userMicroServiceFeignClient.testUserPost(user);
        logger.info("user{}", users);
        return users;
    }

//    @RequestMapping("/testRedis")
//    public String testRedis() {
//        logger.info("开始调用-testRedis");
//        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer );
//        redisTemplate.setValueSerializer(stringSerializer );
//        redisTemplate.setHashKeySerializer(stringSerializer );
//        redisTemplate.setHashValueSerializer(stringSerializer );
//
//        redisTemplate.opsForValue().set("zhangsan","hello ,nice to meet you !");
//        Object zhangsan = redisTemplate.opsForValue().get("zhangsan");
//
//        redisTemplate.opsForList().leftPush("programLanguage","java");
//        redisTemplate.opsForList().leftPush("programLanguage","python");
//        redisTemplate.opsForList().leftPush("programLanguage","c++");
//
//        System.out.println("zhangsan=:  "+zhangsan);
//        String firstname = (String)redisTemplate.boundValueOps("firstname").get();
//        String lastname = (String)redisTemplate.boundValueOps("lastname").get();
//
//        System.out.println("缓存正在设置。。。。。。。。。");
//        redisTemplate.opsForValue().set("key1","value1");
//        redisTemplate.opsForValue().set("key2","value2");
//        redisTemplate.opsForValue().set("key3","value3");
//        redisTemplate.opsForValue().set("key4","value4");
//        System.out.println("缓存已经设置完毕。。。。。。。");
//        String result1=redisTemplate.opsForValue().get("key1").toString();
//        String result2=redisTemplate.opsForValue().get("key2").toString();
//        String result3=redisTemplate.opsForValue().get("key3").toString();
//
//        return "ok" ;
//    }

//    @RequestMapping("/testRedisList")
//    public String testRedisList(){
//        logger.info("开始调用-testRedisList");
////        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
////        redisTemplate.setKeySerializer(stringSerializer );
////        redisTemplate.setValueSerializer(stringSerializer );
////        redisTemplate.setHashKeySerializer(stringSerializer );
////        redisTemplate.setHashValueSerializer(stringSerializer );
//
//        ArrayList<String> stringArrayList = new ArrayList<>();
//        stringArrayList.add("111");
//        stringArrayList.add("222");
//        stringArrayList.add("333");
//
//        ArrayList<String> stringArrayList2 = new ArrayList<>();
//        stringArrayList2.add("444");
//        stringArrayList2.add("555");
//        stringArrayList2.add("666");
//        redisTemplate.opsForList().leftPush("stringArrayList",stringArrayList);
//        redisTemplate.opsForList().rightPush("stringArrayList2",stringArrayList2);
//
//        BoundListOperations<String, Object> list = redisTemplate.opsForList().getOperations().boundListOps("stringArrayList");
//        List<Object> range = list.range(0, -1);
//        for (Object o : range) {
//            System.out.println("*****o***********"+o.toString());
//        }
//        List<String> resultList1 = (List<String>)redisTemplate.opsForList().leftPop("stringArrayList");
//        List<String> resultList2 = (List<String>)redisTemplate.opsForList().rightPop("stringArrayList2");
//        System.out.println("resultList1:"+resultList1);
//        System.out.println("resultList2:"+resultList2);
//        return "ok";
//    }


}
