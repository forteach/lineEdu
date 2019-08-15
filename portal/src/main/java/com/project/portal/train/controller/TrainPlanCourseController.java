package com.project.portal.train.controller;

import com.project.train.service.TrainPlanCourseService;
import com.project.train.service.TrainPlanService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:45
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训计划课程管理", tags = {"培训计划课程管理"})
@RequestMapping(path = "/trainPlanCourse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainPlanCourseController {

    private final TrainPlanCourseService trainPlanCourseService;

    public TrainPlanCourseController(TrainPlanCourseService trainPlanCourseService) {
        this.trainPlanCourseService = trainPlanCourseService;
    }
}