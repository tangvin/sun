package com.example.thread.concurrent_01.ch3;

import java.util.concurrent.atomic.AtomicStampedReference;

/*演示带版本戳的原子操作类*/
public class UseAtomicStampedReference {

    static AtomicStampedReference<String> asr = new AtomicStampedReference<>("king", 0);

    public static void main(String[] args) throws InterruptedException {
        //拿到当前的版本号(旧)
        final int oldStamp = asr.getStamp();
        String oldReference = asr.getReference();
        System.out.println("oldStamp:" + oldStamp + "  oldReference: " + oldReference);

        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ":当前变量值："
                        + oldReference + "-当前版本戳：" + oldStamp + "-"
                        + asr.compareAndSet(oldReference,
                        oldReference + "+Java", oldStamp, oldStamp + 1));
            }
        });


        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName() + ":当前变量值："
                        + reference + "-当前版本戳：" + asr.getStamp() + "-"
                        + asr.compareAndSet(reference,
                        reference + "+C", oldStamp, oldStamp + 1));
            }
        });

        errorStampThread.start();
        errorStampThread.join();
        rightStampThread.start();
        rightStampThread.join();

        System.out.println(asr.getReference() + "============" + asr.getStamp());

    }
}
