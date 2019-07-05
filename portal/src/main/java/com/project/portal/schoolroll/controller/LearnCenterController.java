package com.project.portal.schoolroll.controller;

import com.project.portal.response.WebResult;
import com.project.schoolroll.service.LearnCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:45
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "学习中心")
@RequestMapping(path = "/learnCenter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LearnCenterController {
    private final LearnCenterService learnCenterService;

    public LearnCenterController(LearnCenterService learnCenterService) {
        this.learnCenterService = learnCenterService;
    }

    @ApiOperation(value = "查询中心信息")
    @GetMapping(path = "/")
    public WebResult findAll(){
        return WebResult.okResult(learnCenterService.findAll());
    }
}
