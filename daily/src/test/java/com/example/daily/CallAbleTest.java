package com.example.daily;

import java.util.concurrent.Callable;

public class CallAbleTest implements Callable {
    @Override
    public String call() throws Exception {
        return "String";
    }
}
