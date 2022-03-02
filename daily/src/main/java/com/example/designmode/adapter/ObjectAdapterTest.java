package com.example.designmode.adapter;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/27 22:34
 */
public class ObjectAdapterTest {

    public static void main(String[] args) {
        System.out.println("对象适配器模式测试：");
        Adapter adapter = new Adapter();
        Target target = new ObjectAdapter(adapter);
        target.request();
    }
}
