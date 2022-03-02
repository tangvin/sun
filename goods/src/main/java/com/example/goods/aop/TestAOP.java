package com.example.goods.aop;

import com.example.goods.api.GoodsController;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TestAOP {

    Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Around("execution(* com.example.goods.api.*.*(..))")
    public Object aroundInvoke(ProceedingJoinPoint point) throws Throwable {
        logger.info("GoodsController之前执行~~~~~~~~~~~~~~~~before");
        System.out.println("GoodsController之前执行~~~~~~~~~~~~~~~~before");

        System.out.println("---------------------------------------------------------------");
        Object proceed = point.proceed(point.getArgs());
        System.out.println("---------------------------------------------------------------");

        logger.info("GoodsController之后执行~~~~~~~~~~~~~~~~after");
        System.out.println("GoodsController之后执行~~~~~~~~~~~~~~~~after");
        System.out.println("");
        return proceed;
    }

}
