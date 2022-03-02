package com.example.thread.thread1;

import java.util.concurrent.Callable;

public class ImplCallable implements Callable {
    @Override
    public Object call() throws Exception {
        int i = 0;
        System.out.println("当前线程名称：" + Thread.currentThread().getName() + "");
        return i++;
    }
}
