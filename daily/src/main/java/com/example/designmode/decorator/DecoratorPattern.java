package com.example.designmode.decorator;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/27 23:08
 */
public class DecoratorPattern {
    public static void main(String[] args) {
        Component p = new ConcreteComponent();
        p.operation();
        System.out.println("---------------------------------");
        Component d = new ConcreteDecorator(p);
        d.operation();
    }
}
