package com.example.designmode.adapter;

/**
 * @Description 对象适配器类
 * @Author TY
 * @Date 2021/2/27 22:31
 */
public class ObjectAdapter implements Target {

    private Adapter adapter;

    public ObjectAdapter(Adapter adapter) {
        this.adapter = adapter;
    }


    public void request() {
        adapter.specificRequest();
    }
}
