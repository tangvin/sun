package com.example.user.batch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author TY
 * @description
 * @date 2021/9/21 10:03
 */
public class DateTools {

    //自动获取当前日期
    public static String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
