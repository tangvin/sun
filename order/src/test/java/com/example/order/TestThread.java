package com.example.order;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/10  22:35
 * @Version: 1.0
 * @Modified: By:
 */
public class TestThread implements Runnable {


    @Override
    public void run() {
        int x = 0;
        int y = 0;
        synchronized (this) {
            x = x++;
            y = y++;
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread();
        Thread thread2 = new Thread();
        thread1.start();

    }
}
