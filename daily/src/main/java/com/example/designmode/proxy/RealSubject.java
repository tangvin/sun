package com.example.designmode.proxy;

/**
 * @Description 真实主题
 * @Author TY
 * @Date 2021/2/27 21:53
 */
public class RealSubject implements Subject {
    @Override
    public void Request() {
        System.out.println("访问真实主题方法...");
    }
}
