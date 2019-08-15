package com.project.portal.train.controller;

import com.project.train.service.ClassFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:38
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "班级资料管理", tags = {"班级资料管理"})
@RequestMapping(path = "/classFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClassFileController {
    private final ClassFileService classFileService;

    public ClassFileController(ClassFileService classFileService) {
        this.classFileService = classFileService;
    }
}
