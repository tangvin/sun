package com.example.lambda;

import lombok.Data;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/20   10:17
 * @version: 1.0
 * @modified:
 */
@Data
public class Person {
    private String name;
    private Integer age;
    private String score;

    public Person(){

    }

    public Person(String name, Integer age, String score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public Person(Integer age) {
        this.age = age;
    }
}
