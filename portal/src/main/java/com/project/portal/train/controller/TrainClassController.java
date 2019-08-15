package com.project.portal.train.controller;

import com.project.train.service.TrainClassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:43
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训项目班级管理", tags = {"培训项目班级管理"})
@RequestMapping(path = "/trainClass", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainClassController {
    private final TrainClassService trainClassService;

    public TrainClassController(TrainClassService trainClassService) {
        this.trainClassService = trainClassService;
    }
}
