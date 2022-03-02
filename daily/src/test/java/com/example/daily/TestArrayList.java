package com.example.daily;

import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/17  13:11
 * @Version: 1.0
 * @Modified: By:
 */
public class TestArrayList {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(2, 9, 8, 2, 4));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                list.remove(i);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));

        }
    }

    @Async
    public void test() {

    }
}
