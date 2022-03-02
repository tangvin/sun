package com.example.designmode.decorator;

/**
 * @Description 抽象装饰角色
 * @Author TY
 * @Date 2021/2/27 23:09
 */
public class Decorator implements Component {

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }


    @Override
    public void operation() {
        component.operation();
    }
}
