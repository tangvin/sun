package com.example.user.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/11 11:55
 */
@EnableWebMvc
@Configuration
@ComponentScan
public class WebAppConfigurer implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //访问速率限制

        //参数签名认证

        //公共参数校验

        //交易参数校验
//        registry.addInterceptor(null);

    }
}
