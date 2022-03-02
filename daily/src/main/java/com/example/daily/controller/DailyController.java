package com.example.daily.controller;

import com.example.daily.entity.Daily;
import com.example.daily.service.DailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/5  11:25
 * @Version: 1.0
 * @Modified: By:
 */
@RequestMapping("/daily")
//@RestController
@Controller
public class DailyController {

    private final Logger logger = LoggerFactory.getLogger(DailyController.class);

    @Autowired
    private DailyService dailyService;
    @Autowired
    private RestTemplate restTemplate;

    //思路：页面跳转
    @RequestMapping("/toDailyView")
    public ModelAndView toDaily() {
        ModelAndView modelAndView = new ModelAndView("hello");
        Daily daily = dailyService.findById(1);
        modelAndView.addObject("dailyDate", daily.getDailyDate());
        modelAndView.addObject("ByPerson", daily.getByPerson());
        modelAndView.addObject("dailyContent", daily.getDailyContent());
        return modelAndView;
    }

    //思路：页面跳转
    @RequestMapping("/toDailyViewJsp")
    public ModelAndView toDailyViewJsp() {
        ModelAndView modelAndView = new ModelAndView("daily");
        Daily daily = dailyService.findById(1);
        modelAndView.addObject("dailyDate", daily.getDailyDate());
        modelAndView.addObject("ByPerson", daily.getByPerson());
        modelAndView.addObject("dailyContent", daily.getDailyContent());
        return modelAndView;
    }

    @RequestMapping(value = "/totest")
    public String daily() {
        return "test";
    }

    @RequestMapping("/getDailyById")
    public Daily getUserById(String dailyId) {
        logger.debug("param id : {}", dailyId);
        return this.dailyService.findById(Integer.parseInt(dailyId));
    }


//    @RequestMapping("/getDailyByEureKa")
//    public Object getDailyByEureKa(String orderId) {
////        String url = "http://192.168.3.5:8081/user/getById?id=" + userId;
//        String url = "http://microservice-order/order/getOrderById?id=" + orderId;
//        Object result = restTemplate.getForEntity(url, Object.class);
//        return result;
//    }

    @PostMapping("/testDailyPost")
    public Daily testUserPost(@RequestBody Daily daily) {
        logger.info("daily{}", daily);
        return daily;
    }

}
