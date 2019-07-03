package com.project.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
@Api(value = "测试")
public class HelloController {

    @GetMapping("/hello")
    @ApiOperation(value = "测试hello")
    public String locale(){
        System.out.println("Controller is running !");
        return "Hello";
    }
}
