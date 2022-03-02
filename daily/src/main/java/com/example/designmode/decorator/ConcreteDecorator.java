package com.example.designmode.decorator;

/**
 * @Description 具体装饰角色
 * @Author TY
 * @Date 2021/2/27 23:11
 */
public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }


    public void operation() {
        super.operation();
        addedFunction();
    }

    public void addedFunction() {
        System.out.println("为具体构件角色增加额外的功能addedFunction()");
    }
}
