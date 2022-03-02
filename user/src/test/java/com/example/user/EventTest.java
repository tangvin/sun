package com.example.user;

import com.example.user.bean.EventAnalysis;
import com.example.user.dao.EventAnalysisMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.applet.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventTest {

    @Autowired
    EventAnalysisMapper eventAnalysisMapper;

    @Test
    public void test1() {
        List<EventAnalysis> eventAnalyses = eventAnalysisMapper.selectPageInfo();
        System.out.println("eventAnalyses" + eventAnalyses.toString());
        System.out.println("System.in=="+System.in);
    }

    @Test
    public void test() throws IOException {
        char c;
        // 使用 System.in 创建 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输22入字符, 按下 'q' 键退出。");
        // 读取字符
        do {
            c = (char) br.read();
            System.out.println(c);
        } while (c != 'q');
    }

    @Test
    public void test2() throws IOException {

    }





}
