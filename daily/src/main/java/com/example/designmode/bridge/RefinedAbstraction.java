package com.example.designmode.bridge;

/**
 * @Description 扩展抽象化角色
 * @Author TY
 * @Date 2021/2/28 13:35
 */
public class RefinedAbstraction extends Abstraction {

    protected RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void Operation() {
        System.out.println("扩展抽象化(Refined Abstraction)角色被访问");
        implementor.OperationImpl();
    }
}
