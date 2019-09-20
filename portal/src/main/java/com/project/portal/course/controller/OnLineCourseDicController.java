package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.OnLineCourseDic;
import com.project.course.service.OnLineCourseDicService;
import com.project.portal.course.request.OnLineCourseDicSaveUpdateRequest;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 11:40
 * @Version: 1.0
 * @Description:
 */
@RestController
@Api(value = "在线项目课程字典管理", tags = {"在线项目课程字典管理"})
@RequestMapping(path = "/onLineCourseDic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OnLineCourseDicController {
    private final OnLineCourseDicService onLineCourseDicService;
    private final TokenService tokenService;

    public OnLineCourseDicController(OnLineCourseDicService onLineCourseDicService, TokenService tokenService) {
        this.onLineCourseDicService = onLineCourseDicService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "在线项目课程字典保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataType = "string", paramType = "form")
            //@ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody OnLineCourseDicSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        OnLineCourseDic onLineCourseDic = new OnLineCourseDic();
        BeanUtil.copyProperties(request, onLineCourseDic);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        onLineCourseDic.setCreateUser(userId);
        onLineCourseDic.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getCourseId())) {
            String centerAreaId = tokenService.getCenterAreaId(token);
            onLineCourseDic.setCenterAreaId(centerAreaId);
            return WebResult.okResult(onLineCourseDicService.save(onLineCourseDic));
        } else {
            return WebResult.okResult(onLineCourseDicService.update(onLineCourseDic));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "在线项目课程字典列表")
    @PostMapping(path = "/findAll")
    @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", example = "没有此参数查询全部", paramType = "query")
    public WebResult findAll(@RequestBody String centerAreaId) {
        String centerId = JSONObject.parseObject(centerAreaId).getString("centerAreaId");
        if (StrUtil.isBlank(centerId)) {
            return WebResult.okResult(onLineCourseDicService.findAll());
        }
        return WebResult.okResult(onLineCourseDicService.findAllByCenterAreaId(JSONObject.parseObject(centerAreaId).getString("centerAreaId")));
    }

    @UserLoginToken
    @PostMapping(path = "/removeByCourseId")
    @ApiOperation(value = "移除课程字典中的课程信息")
    @ApiImplicitParam(name = "courseId", dataType = "string", required = true, paramType = "form")
    public WebResult removeByCourseId(@RequestBody String courseId) {
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        onLineCourseDicService.removeByCourseId(JSONObject.parseObject(courseId).getString("courseId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @PostMapping(path = "/deleteByCourseId")
    @ApiOperation(value = "删除课程字典中的课程信息")
    @ApiImplicitParam(name = "courseId", dataType = "string", required = true, paramType = "form")
    public WebResult deleteByCourseId(@RequestBody String courseId) {
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        onLineCourseDicService.deleteByCourseId(JSONObject.parseObject(courseId).getString("courseId"));
        return WebResult.okResult();
    }
}