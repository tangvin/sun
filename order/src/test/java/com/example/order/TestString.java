package com.example.order;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/10  22:29
 * @Version: 1.0
 * @Modified: By:
 */
public class TestString {

    String string = "Hello";
    String s = string + "world";

    public static void main(String[] args) {
        TestString test = new TestString();
        System.out.println(test.string);
        System.out.println(test.s);
    }


}
