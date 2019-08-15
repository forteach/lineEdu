package com.project.portal.train.controller;

import com.project.train.service.TrainCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:44
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训项目课程字典管理", tags = {"培训项目课程字典管理"})
@RequestMapping(path = "/trainCourse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainCourseController {
    private final TrainCourseService trainCourseService;

    public TrainCourseController(TrainCourseService trainCourseService) {
        this.trainCourseService = trainCourseService;
    }
}
