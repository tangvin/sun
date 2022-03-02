package com.example.designmode.adapter;

/**
 * @Description 类适配器
 * @Author TY
 * @Date 2021/2/27 22:27
 */
public class ClassAdapterTest {
    public static void main(String[] args) {
        System.out.println("类适配器模式测试：");
        Target target = new ClassAdapter();
        target.request();
    }
}
