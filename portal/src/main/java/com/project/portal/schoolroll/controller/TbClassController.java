package com.project.portal.schoolroll.controller;

import com.project.portal.response.WebResult;
import com.project.schoolroll.service.online.TbClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/class", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "在线班级管理", tags = {"在线班级信息"})
public class TbClassController {
    private final TbClassService tbClassService;

    public TbClassController(TbClassService tbClassService) {
        this.tbClassService = tbClassService;
    }

    @ApiOperation(value = "查询所有有效班级信息")
    @GetMapping(path ="/list")
    public WebResult findAll(){
        return WebResult.okResult(tbClassService.findAll());
    }
}
