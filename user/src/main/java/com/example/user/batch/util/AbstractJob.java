package com.example.user.batch.util;

import org.springframework.stereotype.Component;

/**
 * @description
 * @author TY
 * @date 2021/8/28 9:18
 */
@Component
public abstract class AbstractJob {

    public void mainProcess(){
        System.out.println("AbstractJob init");
    }



}
