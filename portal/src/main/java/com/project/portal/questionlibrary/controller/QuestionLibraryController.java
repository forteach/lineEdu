//package com.project.portal.questionlibrary.controller;
//
//import cn.hutool.core.util.StrUtil;
//import com.project.base.common.keyword.DefineCode;
//import com.project.base.exception.MyAssert;
//import com.project.portal.response.WebResult;
//import com.project.questionlibrary.service.QuestionLibraryService;
//import com.project.questionlibrary.service.RedisService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 2020/1/6 15:46
// * @version: 1.0
// * @description: 题库
// */
//@RestController
//@RequestMapping(path = "/questionLibary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//@Api(value = "题库", tags = {"题库"})
//public class QuestionLibraryController {
//    private final RedisService redisService;
//    private final QuestionLibraryService questionLibraryService;
//
//    public QuestionLibraryController(RedisService redisService, QuestionLibraryService questionLibraryService) {
//        this.redisService = redisService;
//        this.questionLibraryService = questionLibraryService;
//    }
//
//    @ApiOperation(value = "保存习题库数据", notes = "保存word习题数据")
//    @PostMapping(path = "/importQuestion/{token}")
//    public WebResult importQuestion(@RequestParam("file") MultipartFile file, @PathVariable String token) {
//        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "文件不能为空");
//        MyAssert.isTrue(StrUtil.isBlank(token), DefineCode.ERR0010, "token is null");
//        //导入word 文档数据
//
//        return WebResult.okResult();
//    }
//}