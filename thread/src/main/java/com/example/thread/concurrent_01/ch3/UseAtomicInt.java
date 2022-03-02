package com.example.thread.concurrent_01.ch3;

import java.util.concurrent.atomic.AtomicInteger;

/*演示基本类型的原子操作类*/
public class UseAtomicInt {
    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        int andIncrement = ai.getAndIncrement();
//        int incrementAndGet = ai.incrementAndGet();
        System.out.println("andIncrement=="+andIncrement);
        System.out.println("ai.get()=="+ai.get());
//        System.out.println("incrementAndGet==" + incrementAndGet);
        int addAndGet = ai.addAndGet(10);
        System.out.println("addAndGet==" + addAndGet);
    }
}
