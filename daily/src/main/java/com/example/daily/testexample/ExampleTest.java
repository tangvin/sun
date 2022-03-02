package com.example.daily.testexample;

import java.util.StringJoiner;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/28 16:15
 */
public class ExampleTest {
    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(":", "[", "]");
        stringJoiner.add("George").add("Sally").add("Fred");
        System.out.println(stringJoiner.toString());
    }
}
