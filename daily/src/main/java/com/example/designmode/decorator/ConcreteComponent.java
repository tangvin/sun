package com.example.designmode.decorator;

/**
 * @Description 具体构件角色
 * @Author TY
 * @Date 2021/2/27 23:08
 */
public class ConcreteComponent implements Component {

    public ConcreteComponent() {
        System.out.println("创建具体构件角色");
    }

    public void operation() {
        System.out.println("调用具体构件角色的方法operation()");
    }
}
