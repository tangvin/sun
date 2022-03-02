package com.example.daily.controller;

import org.aspectj.apache.bcel.generic.RET;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/16  21:45
 * @Version: 1.0
 * @Modified: By:
 */
@Controller
public class TestController {

    @RequestMapping("/index")
    public String test2() {
        return "index";
    }
}
