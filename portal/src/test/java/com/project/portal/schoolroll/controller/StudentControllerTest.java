//package com.project.portal.schoolroll.controller;
//
//import cn.hutool.core.lang.Console;
//import cn.hutool.http.HttpUtil;
//import cn.hutool.json.JSONUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-30 16:55
// * @version: 1.0
// * @description:
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class StudentControllerTest {
//
//    @Test
//    public void findStudentExpandDic() {
//        String request = HttpUtil.get("http://localhost:8080/student/findStudentExpandDic");
//        Console.log(JSONUtil.formatJsonStr(request));
//    }
//}