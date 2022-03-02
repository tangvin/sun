package com.example.hystrix.api;


import com.example.hystrix.bean.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/hystrix")
@RestController
public class HystrixController {

    private final Logger logger = LoggerFactory.getLogger(HystrixController.class);


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUserByEurekaByUserId")
    @HystrixCommand(fallbackMethod = "getUserByFallBack")
    public User getOrderById(String userId) {
        logger.info("request getUserById" + userId);
        String url = "http://microservice-user/user/getById?id=" + userId;
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }


    /**
     * 回退方法
     */
    public User getUserByFallBack(String userId) {
        logger.info("回退方法-getUserByFallBack~~~");
        //Mock
        User user = new User();
        user.setName("开启hystrix之后：hello");
        user.setAge(10);
        user.setId(10);
        user.setPhone("开启断路器之后：15712341234");
        user.setAddress("开启断路器之后的数据地址：上海市宝山区");
        System.out.println("user:{"+user.toString()+"}");
        return user;
    }


}
