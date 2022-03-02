package com.example.order.feign;

import com.example.order.bean.User;
import org.springframework.stereotype.Component;

@Component
public class UserMicroServiceFeignImpl  implements UserMicroServiceFeignClient{
    @Override
    public Object getUserById(String id) {
        System.out.println("=====UserMicroServiceFeignImpl=====1");
        return null;
    }

    @Override
    public User testUserPost(User ueser) {
        System.out.println("====UserMicroServiceFeignImpl===2");
        return null;
    }
}
