package com.example.shoppingcart.api;

import com.example.shoppingcart.bean.ShoppingCart;
import com.example.shoppingcart.service.ShoppingCartService;
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
@RequestMapping("/shoppingcart")
@RestController
public class ShoppingCartController {

    private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getShoppingCartById")
    public ShoppingCart getShoppingCartById(String id) {
        logger.debug("param id : {}", id);
        return this.shoppingCartService.findById(Integer.parseInt(id));
    }

//    @RequestMapping("/getByIdEureka")
//    public Object getUserByIdEureka(String userId) {
////        String url = "http://192.168.3.5:8081/user/getById?id=" + userId;
//        String url = "http://microservice-user/user/getById?id=" + userId;
//        Object result = restTemplate.getForEntity(url, Object.class);
//        return result;
//    }

}
