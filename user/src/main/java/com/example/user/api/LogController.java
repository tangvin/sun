//package com.example.user.api;
//
//import com.example.user.service.LogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @ClassName:
// * @Description:
// * @Author: Bruce_T
// * @data: 2019/9/17  18:18
// * @Version: 1.0
// * @Modified: By:
// */
//@Controller
//@RequestMapping("/log")
//public class LogController {
//
//    @Autowired
//    private LogService logService;
//
//    @RequestMapping("/getLogs")
//    public Object getLogs(){
//        List<Map<String, Object>> maps = logService.listLogs();
//        return maps;
//    }
//
//}
