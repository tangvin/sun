package com.example.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @Description
 * @Author TY
 * @Date 2021/9/11 11:21
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value= Exception.class)
    public Response allExceptionHandler(HttpServletRequest request,Exception exception){

        logger.error("拦截到异常",exception);
        Response response = new Response();
        response.setData(null);
        response.getResult().setResultCode(ResultCode.BUSINESS);
        response.getResult().setResultMsg("系统繁忙");
        return response;
    }

}
