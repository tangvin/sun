package com.example.order.test;

import com.example.order.OrderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/18 22:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)// 就是你springboot的启动类
public class JdbcTemplateTest {


    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    public void testJdbcTemplate() {
        int id = 1;
        String sql = "select * from t_order where id =" + id;
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        System.out.println("map===" + map.toString());
    }

}
