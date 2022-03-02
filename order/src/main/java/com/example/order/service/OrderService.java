package com.example.order.service;

import com.example.order.bean.Order;
import com.example.order.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper userMapper;


    public Order findById(Integer id) {
        logger.debug("param id : {}", id);
        Order u = this.userMapper.findById(id);
        logger.debug("result name : {}", u.getName());
        return u;
    }


}
