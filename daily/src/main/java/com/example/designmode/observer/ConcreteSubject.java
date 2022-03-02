package com.example.designmode.observer;

/**
 * @Description 具体目标
 * @Author TY
 * @Date 2021/2/27 23:37
 */
public class ConcreteSubject extends Subject {

    public void notifyObserver() {
        System.out.println("具体目标发生改变...");
        System.out.println("--------------");
        for (Object obs : observers) {
            ((Observer) obs).response();
        }
    }
}
