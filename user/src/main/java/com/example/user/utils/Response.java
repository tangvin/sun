package com.example.user.utils;

import java.io.Serializable;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/11 11:14
 */
public class Response implements Serializable {

    private Result result;
    private Object data;

    public Result getResult() {
        if(this.result == null){
            this.result = new Result();
        }
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
