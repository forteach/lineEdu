package com.project.portal.schoolroll.controller;

import com.project.portal.response.WebResult;
import com.project.schoolroll.service.ExportService;
import com.project.token.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 08:25
 * @Version: 1.0
 * @Description:
 */
@RestController
@Api(value = "导出接口", tags = {"导出相关数据接口"})
@RequestMapping(path = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExportController {

    private final ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

//    @UserLoginToken
    @ApiOperation(value = "导入学生需要信息模版")
    @PostMapping(path = "/exportStudentTemplate")
    public WebResult leadingOutStudentTemplate(){
        exportService.exportStudentTemplate();
        return WebResult.okResult();
    }

    @ApiOperation(value = "导出学生信息")
    @PostMapping(path = "exportStudents")
    public WebResult exportStudents(){

        return WebResult.okResult(exportService.exportStudents());
    }
}
