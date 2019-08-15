package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.TrainClassStuPageRequest;
import com.project.portal.train.request.TrainClassStuSaveUpdateRequest;
import com.project.portal.train.request.TrainCourseSaveUpdateRequest;
import com.project.train.domain.TrainClassStu;
import com.project.train.domain.TrainCourse;
import com.project.train.service.TrainClassStuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:43
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训项目班级学生管理", tags = {"培训项目班级学生管理"})
@RequestMapping(path = "/trainClassStu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainClassStuController {
    private final TrainClassStuService trainClassStuService;

    public TrainClassStuController(TrainClassStuService trainClassStuService) {
        this.trainClassStuService = trainClassStuService;
    }
    @ApiOperation(value = "保存培训项目修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainStuId", value = "培训班级学生编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassId", value = "培训项目班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassName", value = "培训班级名称", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "系统用户编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuName", value = "姓名", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "marriage", value = "民族", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "jobTitle", value = "单位职务", dataType = "string"),
            @ApiImplicitParam(name = "stuIdCard", value = "身份证号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuPhone", value = "联系方式", dataType = "string", paramType = "form"),
    })
    public WebResult saveOrUpdate(@RequestBody TrainClassStuSaveUpdateRequest request) {
        TrainClassStu trainClassStu = new TrainClassStu();
        BeanUtil.copyProperties(request, trainClassStu);
        if (StrUtil.isNotBlank(request.getTrainStuId())) {
            return WebResult.okResult(trainClassStuService.save(trainClassStu));
        } else {
            return WebResult.okResult(trainClassStuService.update(trainClassStu));
        }
    }

    @ApiOperation(value = "项目计划课程列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody TrainClassStuPageRequest request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getClassId(), DefineCode.ERR0010, "班级id不为空");
        return WebResult.okResult(trainClassStuService.findClassPage(request.getClassId(), PageRequest.of(request.getPage(), request.getSize())));
    }

    @ApiOperation(value = "培训项目班级学生列表")
    @PostMapping(path = "/findById")
    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
    public WebResult findById(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(trainClassStuService.findId(JSONObject.parseObject(planId).getString("planId")));
    }
}