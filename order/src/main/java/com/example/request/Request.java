package com.example.request;

import java.io.Serializable;

public class Request<T> implements Serializable {



    private T reqBody;
    private Object reqAttach;
    private String  reqMsgAuth;


    public T getReqBody() {
        return reqBody;
    }

    public void setReqBody(T reqBody) {
        this.reqBody = reqBody;
    }

    public Object getReqAttach() {
        return reqAttach;
    }

    public void setReqAttach(Object reqAttach) {
        this.reqAttach = reqAttach;
    }

    public String getReqMsgAuth() {
        return reqMsgAuth;
    }

    public void setReqMsgAuth(String reqMsgAuth) {
        this.reqMsgAuth = reqMsgAuth;
    }
}
