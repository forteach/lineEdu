package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.TeachPlanPageAllRequest;
import com.project.portal.teachplan.request.TeachPlanSaveUpdateRequest;
import com.project.teachplan.domain.online.TeachPlan;
import com.project.teachplan.service.TeachPlanCourseService;
import com.project.teachplan.service.TeachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.project.portal.request.ValideSortVo.valideSort;

@RestController
@RequestMapping(path = "/teachPlan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "管理学生", tags = {"管理学生信息"})
public class TeachPlanController {

    private final TeachService teachService;

    private final TeachPlanCourseService teachPlanCourseService;

    @Autowired
    public TeachPlanController(TeachService teachService, TeachPlanCourseService teachPlanCourseService) {
        this.teachService = teachService;
        this.teachPlanCourseService = teachPlanCourseService;
    }

    @ApiOperation(value = "保存修改教学计划")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "form"),
            @ApiImplicitParam(name = "planName", dataType = "string", value = "计划名称", paramType = "form"),
            @ApiImplicitParam(name = "courseIds", dataType = "list", value = "课程id集合", paramType = "form"),
            @ApiImplicitParam(name = "classIds", dataType = "list", value = "班级id集合", paramType = "form"),
            @ApiImplicitParam(name = "planAdmin", dataType = "string", value = "计划负责人", paramType = "form"),
            @ApiImplicitParam(name = "startDate", dataType = "string", value = "计划结束时间", paramType = "form"),
            @ApiImplicitParam(name = "endDate", dataType = "string", value = "计划结束时间", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody TeachPlanSaveUpdateRequest request){
        TeachPlan teachPlan = new TeachPlan();
        BeanUtil.copyProperties(request, teachPlan);
        return WebResult.okResult(teachService.saveUpdatePlan(teachPlan, request.getClassIds(), request.getCourseIds()));
    }

    @ApiOperation(value = "分页查询教学计划")
    @PostMapping(path = "/findByPlanIdPageAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
    })
    public WebResult findByPlanIdPageAll(@RequestBody TeachPlanPageAllRequest request){
        valideSort(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getPlanId())) {
            return WebResult.okResult(teachService.findByPlanIdPageAll(request.getPlanId(), PageRequest.of(request.getPage(), request.getSize())));
        }else {
            return WebResult.okResult(teachService.findPageAll(PageRequest.of(request.getPage(), request.getSize())));
        }
    }
    @ApiOperation(value = "移除(逻辑)对应计划的信息")
    @PostMapping(path = "/removeByPlanId")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "form")
    public WebResult removeByPlanId(@RequestBody String plonId){
        MyAssert.isNull(plonId, DefineCode.ERR0010, "计划id不为空");
        teachService.removeByPlanId(JSONObject.parseObject(plonId).getString("planId"));
        return WebResult.okResult();
    }

    @ApiOperation(value = "删除(物理)对应计划的信息")
    @DeleteMapping(path = "/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "form")
    public WebResult deleteByPlanId(@PathVariable String planId){
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        teachService.deleteByPlanId(planId);
        return WebResult.okResult();
    }
    @ApiOperation(value = "查询计划对应课程信息")
    @PostMapping(path = "/course/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "query")
    public WebResult findAllCourseByPlanId(@PathVariable String planId){
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        return WebResult.okResult(teachPlanCourseService.findAllCourseByPlanId(planId));
    }

    @ApiOperation(value = "查询计划对应班级信息")
    @PostMapping(path = "/class/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "query")
    public WebResult findAllClassByPlanId(@PathVariable String planId){
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        return WebResult.okResult(teachService.findAllClassByPlanId(planId));
    }
}
