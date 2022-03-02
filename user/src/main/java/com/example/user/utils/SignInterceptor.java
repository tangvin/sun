package com.example.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/11 11:33
 */
public class SignInterceptor implements HandlerInterceptor {
    private final Logger logger =  LoggerFactory.getLogger(SignInterceptor.class);

//    @Resource
//    private redisApi redisApi;

    public void afterCompletion(HttpServletRequest arg0,Object arg1,Object arg2,Exception arg3) throws Exception{
        //TODO
    }

    public boolean preHandle(HttpServletRequest arg0,Object arg1,Object arg2) throws Exception{
        if(isTestIpAddr(arg0)){
            return true;
        }
        String secureKey = "";
        if(StringUtils.isEmpty(secureKey)){
            Response response = new Response();
            response.setData(null);
            response.getResult().setResultCode(ResultCode.NOT_EXIST);
            logger.error("私钥不存在");

        }
        if(StringUtils.isEmpty(arg0.getParameter("sign"))){
            Response response = new Response();
            response.setData(null);
            response.getResult().setResultCode(ResultCode.NOT_EXIST);
            logger.error("参数验证签名失败！");
            return false;
        }else {
            return true;
        }

    }

    public boolean isTestIpAddr(HttpServletRequest arg){
        return false;
    }
}
