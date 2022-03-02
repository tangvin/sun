package com.example.goods.api;

import com.alibaba.fastjson.JSON;
import com.example.goods.bean.Goods;
import com.example.goods.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/5  11:25
 * @Version: 1.0
 * @Modified: By:
 */
@RequestMapping("/goods")
@RestController
public class GoodsController {

    private final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getGoodsById")
    public Goods getGoodsById(String id) {
        logger.debug("param id : {}", id);
        return this.goodsService.findById(Integer.parseInt(id));
    }


    @RequestMapping("/selectGoodsInfo")
    @ResponseBody
    public List<Map<String, Object>> selectGoodsInfo(String id) {
        logger.debug("param id : {}", id);
        List<Map<String, Object>> list = this.goodsService.selectGoodsInfo(Integer.parseInt(id));
        return list;
    }

    @RequestMapping("/selectGoodsInfoAll")
    @ResponseBody
    public String selectGoodsInfoAll() {
        List<Map<String, Object>> list = this.goodsService.selectGoodsInfoAll();
        String jsonString = JSON.toJSONString(list);
        System.out.println(jsonString);
        return jsonString;
    }

    @RequestMapping("/getByIdEureka")
    public Object getUserByIdEureka(String userId) {
//        String url = "http://192.168.3.5:8081/user/getById?id=" + userId;
        String url = "http://microservice-user/user/getById?id=" + userId;
        Object result = restTemplate.getForEntity(url, Object.class);
        return result;
    }

}
