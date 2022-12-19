package com.example.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2022/12/05   22:23
 * @version: 1.0
 * @modified:
 */
public class TangYinUtils {

    public static void getNextMonth(){

        DateTime dateTime = DateUtil.lastMonth();
        String yyyyMM = dateTime.toString("yyyyMM");

        System.out.println("+++++++++++++++++++++++++yyyyMM@@@@@@@@@@@@@@=="+yyyyMM);
        int i = DateUtil.ageOfNow(new DateTime());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@==i==="+i);
    }


    public static void main(String[] args) {
        System.out.println("42343423");
    }
}
