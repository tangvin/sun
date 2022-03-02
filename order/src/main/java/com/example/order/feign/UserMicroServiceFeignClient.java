package com.example.order.feign;

import com.example.order.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName:
 * @Description:用户为微服务feign
 * @Author: Bruce_T
 * @data: 2019/9/7  8:28
 * @Version: 1.0
 * @Modified: By:
 */
@FeignClient(name = "microservice-user",fallback = UserMicroServiceFeignImpl.class )
public interface UserMicroServiceFeignClient {

    //    @RequestMapping(value = "/user/getById",method = RequestMethod.GET)
    @GetMapping("/user/getById")
    public Object getUserById(@RequestParam("id") String id);


    @PostMapping("/user/testUserPost")
    public User testUserPost(@RequestBody User ueser);

}
