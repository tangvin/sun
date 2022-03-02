package com.example.thread;

import com.example.thread.thread1.ImplCallable;
import com.example.thread.thread1.ImplRunnable;
import com.example.thread.thread1.UseThread;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@SpringBootTest
class ThreadApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
//        UseThread useThread = new UseThread();
//        useThread.start();
//        System.out.println("==========================");
//
//        ImplRunnable implRunnable = new ImplRunnable();
//        Thread thread = new Thread(implRunnable);
//        thread.start();
//        System.out.println("==========================");

        ImplCallable implCallable = new ImplCallable();
        for (int i = 0; i < 10; i++) {
            FutureTask futureTask = new FutureTask<>(implCallable);
            new Thread(futureTask, "" + i).start();
            try {
                //获取子线程返回值
                System.out.println("子线程返回值：" + futureTask.get() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
