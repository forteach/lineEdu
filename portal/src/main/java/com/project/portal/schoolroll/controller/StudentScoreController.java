package com.project.portal.schoolroll.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.StudentScoreRequest;
import com.project.schoolroll.service.StudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:44
 * @version: 1.0
 * @description: 学生成绩controller
 */
@RestController
@RequestMapping(path = "/studentScore", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "学生成绩管理", tags = {"学生成绩管理"})
public class StudentScoreController {
    private final StudentScoreService studentScoreService;

    public StudentScoreController(StudentScoreService studentScoreService) {
        this.studentScoreService = studentScoreService;
    }

    @ApiOperation(value = "查询学生对应成绩信息")
    @PostMapping(path = "/findStudentScore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuId", value = "学生Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "query")
    })
    public WebResult findStudentScore(@RequestBody StudentScoreRequest request){
        MyAssert.isNull(request.getStuId(), DefineCode.ERR0010, "学生id不为空");
        if (StrUtil.isBlank(request.getCourseId())){
            return WebResult.okResult(studentScoreService.findByStudentId(request.getStuId()));
        }else {
            return WebResult.okResult(studentScoreService.findByStudentIdAndCourseId(request.getStuId(), request.getCourseId()));
        }
    }

    @ApiOperation(value = "删除无效的学生成绩信息")
    @DeleteMapping(path = "/deleteByScoreId")
    @ApiImplicitParam(name = "scoreId", value = "成绩信息id", dataType = "string", paramType = "form")
    public WebResult deleteStudentScoreById(@RequestBody String scoreId){
        MyAssert.isNull(scoreId, DefineCode.ERR0010, "成绩id信息不为空");
        studentScoreService.deleteStudentScoreById(JSONObject.parseObject(scoreId).getString("scoreId"));
        return WebResult.okResult();
    }
}
