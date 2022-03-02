package com.example.designmode.observer;

/**
 * @Description 具体观察者1
 * @Author TY
 * @Date 2021/2/27 23:35
 */
public class ConcreteObserver1 implements Observer {
    @Override
    public void response() {
        System.out.println("具体观察者1作出反应！");
    }
}
