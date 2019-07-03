package com.project.portal.schoolroll.controller;

import com.project.schoolroll.service.StudentService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-3 14:26
 * @version: 1.0
 * @description:
 */
@RestController
@RequestMapping(path = "/student", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "管理学生", tags = {"管理学生信息"})
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

}
