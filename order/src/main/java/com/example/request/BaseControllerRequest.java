package com.example.request;

public class BaseControllerRequest<T extends AbstractRequestBodyHeader> extends Request<T>{
    public T getReqBody(){
        return super.getReqBody();
    }

    public void setReqBody(T reqBody){
         super.setReqBody(reqBody);
    }

    @Override
    public String toString() {
        return "BaseControllerRequest{}";
    }
}
