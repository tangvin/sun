package com.example.thread.concurrent_01.ch3;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArray {

    static int[] value = new int[]{0, 1, 2,100};
    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        int i = atomicIntegerArray.get(0);
        System.out.println("i===" + i);
        int andSet = atomicIntegerArray.getAndSet(2, 100);
        int andSet0 = atomicIntegerArray.getAndSet(3, 999);
        System.out.println("andSet==" + andSet);
        System.out.println("andSet0==" + andSet0);
        System.out.println("atomicIntegerArray==" + atomicIntegerArray);
        System.out.println("value[2]==" + value[2]);
    }
}
