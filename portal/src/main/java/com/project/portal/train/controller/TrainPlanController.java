package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.FindTrainPlanAllPageRequest;
import com.project.portal.train.request.TrainProjectPlanSaveRequest;
import com.project.train.domain.TrainProjectPlan;
import com.project.train.service.TrainPlanService;
import io.swagger.annotations.*;
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
 * @date: 19-7-4 09:36
 * @version: 1.0
 * @description: 培训项目计划列表管理
 */
@RestController
@Api(value = "培训项目计划管理", tags = {"培训项目计划管理"})
@RequestMapping(path = "/train/plan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainPlanController {

    private final TrainPlanService trainPlanService;

    private TrainPlanController(TrainPlanService trainPlanService) {
        this.trainPlanService = trainPlanService;
    }

    @ApiOperation(value = "保存培训项目修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pjPlanId", value = "项目计划id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainAreaId", value = "项目领域", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "applyStart", value = "报名开始时间", dataType = "string"),
            @ApiImplicitParam(name = "applyEnd", value = "报名结束时间", dataType = "string"),
            @ApiImplicitParam(name = "trainStart", value = "计划开始执行时间", dataType = "string"),
            @ApiImplicitParam(name = "trainEnd", value = "计划执行结束时间", dataType = "string"),
            @ApiImplicitParam(name = "trainAdmin", value = "计划负责人", dataType = "string"),
            @ApiImplicitParam(name = "trainProjectId", value = "培训项目编号", dataType = "string"),
            @ApiImplicitParam(name = "trainProjectName", value = "项目名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody TrainProjectPlanSaveRequest request) {
        TrainProjectPlan trainProjectPlan = new TrainProjectPlan();
        BeanUtil.copyProperties(request, trainProjectPlan);
        if (StrUtil.isBlank(request.getPjPlanId())) {
            return WebResult.okResult(trainPlanService.save(trainProjectPlan));
        } else {
            return WebResult.okResult(trainPlanService.update(trainProjectPlan));
        }
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(path = "/findById")
    @ApiImplicitParam(name = "planId", value = "项目计划id", dataType = "string", required = true, paramType = "query")
    public WebResult findById(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(trainPlanService.findId(JSONObject.parseObject(planId).getString("planId")));
    }

    @ApiOperation(value = "根据学习中心编号id查询列表(不分页)")
    @PostMapping(path = "/findByCenterAreaId")
    @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query")
    public WebResult findAll(@RequestBody String centerAreaId) {
        MyAssert.isNull(centerAreaId, DefineCode.ERR0010, "学习中心编号id不为空");
        return WebResult.okResult(trainPlanService.findAll(JSONObject.parseObject(centerAreaId).getString("centerAreaId")));
    }

    @ApiOperation(value = "分页查询培训项目列表/获取前多少天项目计划列表")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "string"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody FindTrainPlanAllPageRequest request) {
        valideSort(request.getPage(), request.getPage());
        MyAssert.isNull(request.getCenterAreaId(), DefineCode.ERR0010, "学习中心编号id不为空");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isBlank(String.valueOf(request.getAgoDay()))) {
            return WebResult.okResult(trainPlanService.findAllPage(request.getCenterAreaId(), pageRequest));
        } else {
            return WebResult.okResult(trainPlanService.findAllPage(request.getCenterAreaId(), Integer.parseInt(request.getAgoDay()), pageRequest));
        }
    }
}