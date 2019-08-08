package com.project.portal.user.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.project.portal.user.request.UserLoginRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @Test
    public void login(){
        UserLoginRequest request = new UserLoginRequest();
        request.setPassWord("123456");
        request.setTeacherCode("admin");
        JSON json = JSONUtil.parse(request);
        HttpResponse response = HttpUtil.createPost("http://127.0.0.1:7080/auth/login").body(json).execute();
        Console.log("status : {}, \r\n body : {}", response.getStatus(), JSONUtil.formatJsonStr(response.body()));
    }
}
