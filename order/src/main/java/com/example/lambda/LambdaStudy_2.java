package com.example.lambda;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/20   9:27
 * @version: 1.0
 * @modified:
 */
public class LambdaStudy_2 {

    //消费型Consumer
    //需求：传入一个参数做业务，不需要返回值
    public void happy(double money, Consumer<Double> consumer){
        consumer.accept(money);
    }

    @Test
    public void test1(){
        happy(100,(m)-> System.out.println("花费"+m+"元"));
    }

    /**
     * 供给型接口 Supplier
     * 需求：产生只指定数量的整数，放到集合中，返回集合
     */
    public List<Integer> getNumList(int num , Supplier<Integer> supplier){

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    @Test
    public void test2(){
        List<Integer> numList = getNumList(100, () -> (int) (Math.random() * 100));
        numList.forEach(System.out::print);
    }


    /**
     * 函数型
     * 需求2：传入一个字符串，返回一个字符串
     */
    public String strHandle(String str, Function<String,String> function){
        return function.apply(str);
    }

    @Test
    public void test3(){
        String strHandle = strHandle("china", (x) -> x + "beijing");
        System.out.println(strHandle);
    }

    /**
     * 断言型
     *
     */
    public List<Person> filterStudent(List<Person> list, Predicate<Person> predicate){
        List<Person> result = new ArrayList<>();
        for (Person person : result) {
            if(predicate.test(person)){
                result.add(person);
            }
        }
        return result;
    }


}
