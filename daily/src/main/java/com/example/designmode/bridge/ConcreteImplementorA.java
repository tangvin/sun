package com.example.designmode.bridge;

/**
 * @Description 具体的实现化角色
 * @Author TY
 * @Date 2021/2/28 13:33
 */
public class ConcreteImplementorA implements Implementor {
    @Override
    public void OperationImpl() {
        System.out.println("具体实现化(Concrete Implementor)角色被访问");
    }
}
