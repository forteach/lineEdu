package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.FindTrainClassStuAllPageRequest;
import com.project.portal.train.request.TrainClassStuPageRequest;
import com.project.portal.train.request.TrainClassStuSaveUpdateRequest;
import com.project.token.annotation.UserLoginToken;
import com.project.train.domain.TrainClassStu;
import com.project.train.service.TrainClassStuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @UserLoginToken
    @ApiOperation(value = "保存培训项目修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainStuId", value = "培训班级学生编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassId", value = "培训项目班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassName", value = "培训班级名称", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "系统用户编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainProjectName", value = "培训项目名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuName", value = "姓名", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "nation", value = "民族", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "jobTitle", value = "单位职务", dataType = "string"),
            @ApiImplicitParam(name = "stuIdCard", value = "身份证号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "stuPhone", value = "联系方式", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody TrainClassStuSaveUpdateRequest request) {
        TrainClassStu trainClassStu = new TrainClassStu();
        BeanUtil.copyProperties(request, trainClassStu);
        if (StrUtil.isBlank(request.getTrainStuId())) {
            return WebResult.okResult(trainClassStuService.save(trainClassStu));
        } else {
            return WebResult.okResult(trainClassStuService.update(trainClassStu));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "项目计划课程列表")
    @PostMapping(path = "/findByClassIdAllPage")
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

//    @ApiOperation(value = "培训项目班级学生列表")
//    @PostMapping(path = "/findById")
//    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
//    public WebResult findById(@RequestBody String planId) {
//        MyAssert.isNull(planId, DefineCode.ERR0010, "项目计划id不为空");
//        return WebResult.okResult(trainClassStuService.findId(JSONObject.parseObject(planId).getString("planId")));
//    }

    @UserLoginToken
    @ApiOperation(value = "根据计划id分页查询班级学生")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15"),
            @ApiImplicitParam(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "string"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "query")
    })
    @PostMapping(path = "/findAllPageAll")
    public WebResult findByPjPlanIdPageAll(@RequestBody FindTrainClassStuAllPageRequest request){
        valideSort(request.getPage(), request.getPage());
        if (StrUtil.isNotBlank(request.getAgoDay()) && StrUtil.isNotBlank(request.getPjPlanId())){
            return WebResult.okResult(trainClassStuService.findByCenterAreaIdAndAgoDayAll(request.getCenterAreaId(), request.getPjPlanId(), Integer.parseInt(request.getAgoDay()), PageRequest.of(request.getPage(), request.getSize())));
        }else if (StrUtil.isBlank(request.getAgoDay()) && StrUtil.isNotBlank(request.getPjPlanId())){
            return WebResult.okResult(trainClassStuService.findByPlanIdPageAll(request.getPjPlanId(), PageRequest.of(request.getPage(), request.getSize())));
        }else if (StrUtil.isNotBlank(request.getAgoDay()) && StrUtil.isBlank(request.getPjPlanId())){
            return WebResult.okResult(trainClassStuService.findAgoDay(Integer.parseInt(request.getAgoDay()), PageRequest.of(request.getPage(), request.getSize())));
        }else {
            return WebResult.okResult(trainClassStuService.findAllPage(PageRequest.of(request.getPage(), request.getSize())));
        }
    }
}