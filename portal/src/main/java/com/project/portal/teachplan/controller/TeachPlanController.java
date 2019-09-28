package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.*;
import com.project.teachplan.domain.verify.TeachPlanVerify;
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
            @ApiImplicitParam(name = "planAdmin", dataType = "string", value = "计划负责人", paramType = "form"),
            @ApiImplicitParam(name = "startDate", dataType = "string", value = "计划结束时间", paramType = "form"),
            @ApiImplicitParam(name = "endDate", dataType = "string", value = "计划结束时间", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody TeachPlanSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        TeachPlanVerify teachPlan = new TeachPlanVerify();
        BeanUtil.copyProperties(request, teachPlan);
        teachPlan.setCenterAreaId(tokenService.getCenterAreaId(token));
        teachPlan.setCreateUser(userId);
        teachPlan.setUpdateUser(userId);
        return WebResult.okResult(teachService.saveUpdatePlan(teachPlan));
    }

    @UserLoginToken
    @ApiOperation(value = "保存修改计划对应的班级接口")
    @PostMapping(path = "/saveUpdateClass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "classIds", dataType = "list", value = "班级id集合", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注说明", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdateClass(@RequestBody TeachPlanClassSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isTrue(request.getClassIds().isEmpty(), DefineCode.ERR0010, "班级信息不为空");
        String token = httpServletRequest.getHeader("token");
        String centerAreaId = tokenService.getCenterAreaId(token);
        String userId = tokenService.getUserId(token);
        return WebResult.okResult(teachService.saveUpdatePlanClass(request.getPlanId(), request.getClassIds(), request.getRemark(), centerAreaId, userId));
    }

    @UserLoginToken
    @ApiOperation(value = "保存修改计划对应的课程接口")
    @PostMapping(path = "/saveUpdateCourse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "teacherId", dataType = "string", value = "创建教师id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "courses", dataType = "list", value = "课程id集合", paramType = "form"),
            @ApiImplicitParam(name = "courseId", dataType = "string", value = "课程id", paramType = "form"),
            @ApiImplicitParam(name = "credit", dataType = "string", value = "学分", paramType = "form"),
            @ApiImplicitParam(name = "onLinePercentage", dataType = "int", value = "线上占比", paramType = "form"),
            @ApiImplicitParam(name = "linePercentage", dataType = "int", value = "线下占比", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注说明", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdateCourse(@RequestBody TeachPlanCourseSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isTrue(request.getCourses().isEmpty(), DefineCode.ERR0010, "课程信息不为空");
        String token = httpServletRequest.getHeader("token");
        String centerAreaId = tokenService.getCenterAreaId(token);
        String userId = tokenService.getUserId(token);
        return WebResult.okResult(teachService.saveUpdatePlanCourse(request.getPlanId(), request.getCourses(), request.getRemark(), centerAreaId, userId));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询教学计划")
    @PostMapping(path = "/findByPlanIdPageAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "query"),
            @ApiImplicitParam(name = "isValidated", value = "修改状态", dataType = "0 (同意) 1 (已经提交) 2 (不同意)", paramType = "form"),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", required = true, paramType = "query")
    })
    public WebResult findByPlanIdPageAll(@RequestBody TeachPlanPageAllRequest request, HttpServletRequest httpServletRequest) {
        valideSort(request.getPage(), request.getSize());
        String token = httpServletRequest.getHeader("token");
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (tokenService.isAdmin(token)) {
            if (StrUtil.isNotBlank(request.getPlanId())) {
                return WebResult.okResult(teachService.findAllPageByPlanIdDto(request.getPlanId(), pageRequest));
            }
            return WebResult.okResult(teachService.findAllPageDto(pageRequest));
        } else {
            String centerAreaId = tokenService.getCenterAreaId(token);
            if (StrUtil.isNotBlank(request.getPlanId())) {
                return WebResult.okResult(teachService.findAllPageDtoByCenterAreaIdAndPlanId(centerAreaId, request.getPlanId(), pageRequest));
            } else {
                return WebResult.okResult(teachService.findAllPageDtoByCenterAreaId(centerAreaId, pageRequest));
            }
        }
    }

    @UserLoginToken
    @ApiOperation(value = "移除(逻辑)对应计划的信息")
    @PostMapping(path = "/removeByPlanId")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form")
    public WebResult removeByPlanId(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        teachService.removeByPlanId(JSONObject.parseObject(planId).getString("planId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除(物理)对应计划的信息")
    @DeleteMapping(path = "/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form")
    public WebResult deleteByPlanId(@PathVariable String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        teachService.deleteByPlanId(planId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询计划对应课程信息")
    @PostMapping(path = "/course/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "query")
    public WebResult findAllCourseByPlanId(@PathVariable String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        return WebResult.okResult(teachPlanCourseService.findAllCourseByPlanId(planId));
    }

    @UserLoginToken
    @ApiOperation(value = "查询计划对应班级信息")
    @PostMapping(path = "/class/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "query")
    public WebResult findAllClassByPlanId(@PathVariable String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        return WebResult.okResult(teachService.findAllClassByPlanId(planId));
    }

    @ApiOperation(value = "更新教学计划状态")
    @PutMapping(path = "/status/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form")
    public WebResult updateStatus(@PathVariable String planId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        teachService.updateStatus(planId, userId);
        return WebResult.okResult();
    }

    @ApiOperation(value = "审核教学计划")
    @PostMapping(path = "/verifyTeachPlan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "isValidated", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注信息", dataType = "string", paramType = "form")
    })
    public WebResult verifyTeachPlan(TeachPlanVerifyRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isNull(request.getIsValidated(), DefineCode.ERR0010, "计划状态不能为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        teachService.verifyTeachPlan(request.getPlanId(), request.getIsValidated(), request.getRemark(), userId);
        return WebResult.okResult();
    }
}
