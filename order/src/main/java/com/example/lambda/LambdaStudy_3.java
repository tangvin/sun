package com.example.lambda;

import com.mysql.cj.conf.RuntimeProperty;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/20   10:20
 * @version: 1.0
 * @modified:
 */
public class LambdaStudy_3 {
    /**
     * 对象::实例方法
     *
     */
    @Test
    public void test1(){
//        Consumer<String> consumer =  (x)-> System.out.println(x);
//        PrintStream printStream = System.out;
        //注意：接口的抽象方法的形参表，返回类型需要和调用的类方法形参表返回类型保持一致
        Consumer<String> consumer = System.out::println;
        consumer.accept("123456");
    }

    /**
     * 类::静态方法
     */
    @Test
    public void test2(){
//        PrintStream printStream = System.out;
        //注意：接口的抽象方法的形参表，返回类型需要和调用的类方法形参表返回类型保持一致
        Comparator<Integer> comparator = (x,y)->Integer.compare(x,y);
        Comparator<Integer> comparator2 = Integer::compare;
    }


    /**
     * 类::实例方法名
     */
    @Test
    public void test3(){
        //需求：比较2个字符串是否相等
        //需求：第一个参数是实例方法的调用者，第二个参数是的方法传入参数
        BiPredicate<String,String> bp = (x,y)->x.equals(y);
        BiPredicate<String,String> bp2 = String::equals;
    }

    /**
     * 构造器引用
     */
    @Test
    public void test4(){
        Supplier<Person> supplier = Person::new;
        System.out.println(supplier.get());
        Function<Integer,Person> person = Person::new;
        System.out.println(person.apply(10));
    }


}
