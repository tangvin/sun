package com.example.designmode.observer;

/**
 * @Description /具体观察者1
 * @Author TY
 * @Date 2021/2/27 23:36
 */
public class ConcreteObserver2 implements Observer {
    @Override
    public void response() {
        System.out.println("具体观察者2作出反应！");
    }
}
