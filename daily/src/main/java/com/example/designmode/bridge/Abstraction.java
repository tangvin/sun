package com.example.designmode.bridge;

/**
 * @Description 抽象化角色
 * @Author TY
 * @Date 2021/2/28 13:34
 */
public abstract class Abstraction {
    protected Implementor implementor;


    protected Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public abstract void Operation();
}
