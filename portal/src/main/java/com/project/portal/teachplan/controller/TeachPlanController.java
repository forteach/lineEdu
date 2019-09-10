package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.TeachPlanClassSaveUpdateRequest;
import com.project.portal.teachplan.request.TeachPlanCourseSaveUpdateRequest;
import com.project.portal.teachplan.request.TeachPlanPageAllRequest;
import com.project.portal.teachplan.request.TeachPlanSaveUpdateRequest;
import com.project.teachplan.domain.online.TeachPlan;
import com.project.teachplan.service.TeachPlanCourseService;
import com.project.teachplan.service.TeachService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;

@RestController
@RequestMapping(path = "/teachPlan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "在线计划管理", tags = {"在线计划管理"})
public class TeachPlanController {

    private final TeachService teachService;
    private final TokenService tokenService;

    private final TeachPlanCourseService teachPlanCourseService;

    @Autowired
    public TeachPlanController(TeachService teachService, TeachPlanCourseService teachPlanCourseService, TokenService tokenService) {
        this.teachService = teachService;
        this.teachPlanCourseService = teachPlanCourseService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "保存修改教学计划")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "form"),
            @ApiImplicitParam(name = "planName", dataType = "string", value = "计划名称", paramType = "form"),
//            @ApiImplicitParam(name = "courseIds", dataType = "list", value = "课程id集合", paramType = "form"),
//            @ApiImplicitParam(name = "classIds", dataType = "list", value = "班级id集合", paramType = "form"),
            @ApiImplicitParam(name = "planAdmin", dataType = "string", value = "计划负责人", paramType = "form"),
            @ApiImplicitParam(name = "startDate", dataType = "string", value = "计划结束时间", paramType = "form"),
            @ApiImplicitParam(name = "endDate", dataType = "string", value = "计划结束时间", paramType = "form"),
            @ApiImplicitParam(name = "teacherId", dataType = "string", value = "教师Id", paramType = "string")
    })
    public WebResult saveUpdate(@RequestBody TeachPlanSaveUpdateRequest request, HttpServletRequest httpServletRequest){
//        if (StrUtil.isBlank(request.getTeacherId())){
//            request.setTeacherId(tokenService.getTeacherId(httpServletRequest.getHeader("token")));
//        }
        TeachPlan teachPlan = new TeachPlan();
        BeanUtil.copyProperties(request, teachPlan);
        return WebResult.okResult(teachService.saveUpdatePlan(teachPlan));
    }

    @ApiOperation(value = "保存修改计划对应的班级接口")
    @PostMapping(path = "/saveUpdateClass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classIds", dataType = "list", value = "班级id集合", paramType = "form")
    })
    public WebResult saveUpdateClass(@RequestBody TeachPlanClassSaveUpdateRequest request){
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isTrue(request.getClassIds().isEmpty(), DefineCode.ERR0010, "班级信息不为空");
        return WebResult.okResult(teachService.saveUpdatePlanClass(request.getPlanId(), request.getClassIds()));
    }

    @ApiOperation(value = "保存修改计划对应的课程接口")
    @PostMapping(path = "/saveUpdateCourse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "teacherId", dataType = "string", value = "创建教师id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "courses", dataType = "list", value = "课程id集合", paramType = "form"),
            @ApiImplicitParam(name = "courseId", dataType = "string", value = "课程id", paramType = "form"),
            @ApiImplicitParam(name = "credit", dataType = "string", value = "学分", paramType = "form"),
            @ApiImplicitParam(name = "onLinePercentage", dataType = "int", value = "线上占比", paramType = "form"),
            @ApiImplicitParam(name = "linePercentage", dataType = "int", value = "线下占比", paramType = "form")
    })
    public WebResult saveUpdateCourse(@RequestBody TeachPlanCourseSaveUpdateRequest request){
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
//        MyAssert.isNull(request.getTeacherId(), DefineCode.ERR0010, "教师id不为空");
        MyAssert.isTrue(request.getCourses().isEmpty(), DefineCode.ERR0010, "课程信息不为空");
        return WebResult.okResult(teachService.saveUpdatePlanCourse(request.getPlanId(), request.getCourses()));
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
    public WebResult removeByPlanId(@RequestBody String planId){
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        teachService.removeByPlanId(JSONObject.parseObject(planId).getString("planId"));
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
