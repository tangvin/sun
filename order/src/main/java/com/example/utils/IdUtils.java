package com.example.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2023/01/08   19:40
 * @version: 1.0
 * @modified:
 */
public class IdUtils {

    /**
     * 按时间戳+随机数+用户ID+原名生成不重复新文件名的方案一
     * 此方案胜在一目了然，败在长度
     * @param uid
     * @param originalName
     * @return
     */
    public static String generateId1(long uid,String originalName) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");

        StringBuilder sb=new StringBuilder();
        sb.append(sdf.format(new Date()));
        Random rnd=new Random();
        sb.append(rnd.nextInt(100));
        sb.append(uid);
        sb.append("_"+originalName);

        return sb.toString();
    }


    /**
     * 按时间戳+随机数+用户ID+原名生成不重复新文件名的方案二
     * 此方案胜在相对短小，有些神秘感，败在还原不便
     * @param uid
     * @param originalName
     * @return
     */
    public static String generateId2(long uid,String originalName) {
        LocalDate today= LocalDate.now();
        int yearsAfter2020=today.getYear()-2020;
        int days=today.getDayOfYear();

        LocalTime now= LocalTime.now();
        int seconds=now.toSecondOfDay();

        // %02d 数字两位左补零；%-nS 固定长度为n的字符串，如要继续缩短可以去掉定长
        String prefix=String.format("%02d%-3s%-5s", yearsAfter2020,Long.toHexString(days),Long.toHexString(seconds));

        StringBuilder sb=new StringBuilder();
        sb.append(prefix);
        Random rnd=new Random();
        sb.append(rnd.nextInt(100));
        sb.append(uid);
        sb.append("_"+originalName);

        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(generateId1(4,"test"));
    }
}
