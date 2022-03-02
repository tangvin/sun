package com.example.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/11 12:09
 */
public class TokenTool {

    private final Logger logger = LoggerFactory.getLogger(TokenTool.class);

    public String createToken(){
        String token = "Msk@D"+System.currentTimeMillis()+"UEBH&LO";
        logger.info("create token is:{}", token);
        return MD5Util.string2Md5(token);
    }

//    public static void main(String[] args) throws IOException {
//        char c;
//        // 使用 System.in 创建 BufferedReader
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("输22入字符, 按下 'q' 键退出。");
//        // 读取字符
//        do {
//            c = (char) br.read();
//            System.out.println(c);
//        } while (c != 'q');
//    }

    public static void main(String[] args) throws IOException {
        // 使用 System.in 创建 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Enter lines of text.");
        System.out.println("Enter 'end' to quit.");
        do {
            str = br.readLine();
            System.out.println(str);
        } while (!str.equals("end"));
    }
}
