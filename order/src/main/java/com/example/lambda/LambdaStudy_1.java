package com.example.lambda;


import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class LambdaStudy_1 {
    /**
     * 语法1：无参、无返回值
     */
    public void test1(){
        Runnable runnable = ()->{
            System.out.println("111");
        };
    }

    /**
     * 语法2：有一个参数、无返回值
     */
    public void test2(){
        Consumer<String> consumer = (x)->{System.out.println("121212");};
        consumer.accept("222");
    }

    /**
     * 语法3：只有一个参数，省略括号
     */
    public void test3(){
        Consumer<String> consumer = x->{System.out.println("121212");};
        consumer.accept("333");
    }

    /**
     * 语法4：有2个参数，有返回值、lambda中有多条语句
     */
    public void test4(){
        Comparator<Integer> comparator = (x,y)->{
            System.out.println("444");
            return Integer.compare(x,y);
        };
    }

    /**
     * 语法5：lambda中只有一条语句，return 和 大括号都可以省略
     */
    public void test5(){
        Comparator<Integer> comparator = (x, y)->Integer.compare(x,y);
    }
    public void test5_1(){
        Comparator<Integer> comparator = Integer::compare;
        int compare = comparator.compare(1, 2);
    }

    /**
     * 语法6：参数指定类型  JVM编译器上下文推断 数据类型
     */
    public void test6(){
        Comparator<Integer> comparator = (Integer x,Integer y)-> Integer.compare(x,y);
        String[] strings = {"1","2"};
        String[] strings1;
        strings1 = new String[]{"1", "2"};
    }


}
