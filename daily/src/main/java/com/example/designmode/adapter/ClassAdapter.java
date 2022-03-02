package com.example.designmode.adapter;

/**
 * @Description 类适配器类
 * @Author TY
 * @Date 2021/2/27 22:27
 */
public class ClassAdapter extends Adapter implements Target {
    @Override
    public void request() {
        specificRequest();
    }
}
