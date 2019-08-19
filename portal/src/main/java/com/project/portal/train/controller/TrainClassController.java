package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.TrainClassFindAllPage;
import com.project.portal.train.request.TrainClassSaveUpdateRequest;
import com.project.train.domain.TrainClass;
import com.project.train.service.TrainClassService;
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
@Api(value = "培训项目班级管理", tags = {"培训项目班级管理"})
@RequestMapping(path = "/trainClass", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainClassController {
    private final TrainClassService trainClassService;

    public TrainClassController(TrainClassService trainClassService) {
        this.trainClassService = trainClassService;
    }

    @ApiOperation(value = "培训项目班级保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainClassId", value = "培训项目班级编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainClassName", value = "培训班级名称", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classAdmin", value = "培训班级管理员", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classAdminTel", value = "培训班级管理员电话", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "lineOnLine", value = "线上线下", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody TrainClassSaveUpdateRequest request) {
        TrainClass trainClass = new TrainClass();
        BeanUtil.copyProperties(request, trainClass);
        if (StrUtil.isBlank(request.getTrainClassId())) {
            return WebResult.okResult(trainClassService.save(trainClass));
        } else {
            return WebResult.okResult(trainClassService.update(trainClass));
        }
    }

    @ApiOperation(value = "培训项目班级列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody TrainClassFindAllPage request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getPjPlanId(), DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(trainClassService.findPlanPage(request.getPjPlanId(), PageRequest.of(request.getPage(), request.getSize())));
    }

    @ApiOperation(value = "培训项目班级管理")
    @PostMapping(path = "/findById")
    @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", required = true, paramType = "query")
    public WebResult findById(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(trainClassService.findId(JSONObject.parseObject(planId).getString("planId")));
    }
}