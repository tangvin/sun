package com.example.lambda;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/21   22:23
 * @version: 1.0
 * @modified:
 */
public class TestExecute {

//    public void main(String[] args) {
//        Executor exe = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 3; i++) {
//            ExeThreads ex = new ExeThreads();
//            exe.execute(ex);
//        }
//    }

    public static void main(String[] args) {
        Executor exe = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 3; i++) {
            ExeThreads ex = new ExeThreads();
            exe.execute(ex);
        }
    }

     static class  ExeThreads implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("run start-----------------" + System.currentTimeMillis()/1000/1000);
                Thread.sleep(10 * 1000);
                System.out.println("run end-----------------" + System.currentTimeMillis()/1000/1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}




