//package com.example.user.service;
//
//import com.example.user.bean.User;
//import com.example.user.mapper.UserMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//    private final Logger logger = LoggerFactory.getLogger(UserService.class);
//
//    @Autowired
//    private UserMapper userMapper;
//
//    public User findById(Integer id) {
//        logger.debug("param id : {}", id);
//        User u = this.userMapper.findById(id);
//        logger.debug("result name : {}", u.getName());
//        return u;
//    }
//}
