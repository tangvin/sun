package com.example.consumer.manager.ibb;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/24   23:11
 * @version: 1.0
 * @modified:
 */
public class Test3 implements StrategyMode{
    @Override
    public String doSubmit() {
        System.out.println("333");
        return "333";
    }
}
