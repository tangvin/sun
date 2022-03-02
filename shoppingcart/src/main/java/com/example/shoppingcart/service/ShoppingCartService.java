package com.example.shoppingcart.service;


import com.example.shoppingcart.bean.ShoppingCart;
import com.example.shoppingcart.mapper.ShoppingCartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    private final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    public ShoppingCart findById(Integer id) {
        logger.debug("param id : {}", id);
        ShoppingCart u = this.shoppingCartMapper.findById(id);
        logger.debug("result name : {}", u.getGoods());
        return u;
    }
}
