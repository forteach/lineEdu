package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.TrainCourseSaveUpdateRequest;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.train.domain.TrainCourse;
import com.project.train.service.TrainCourseService;
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

import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:44
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训项目课程字典管理", tags = {"培训项目课程字典管理"})
@RequestMapping(path = "/trainCourse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainCourseController {
    private final TrainCourseService trainCourseService;
    private final TokenService tokenService;

    public TrainCourseController(TrainCourseService trainCourseService, TokenService tokenService) {
        this.trainCourseService = trainCourseService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "培训项目课程字典保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "培训课程编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "培训课程名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "trainAreaId", value = "培训项目领域", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody TrainCourseSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        TrainCourse trainCourse = new TrainCourse();
        BeanUtil.copyProperties(request, trainCourse);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        trainCourse.setUpdateUser(userId);
        if (StrUtil.isBlank(request.getCourseId())) {
            String centerAreaId = tokenService.getCenterAreaId(token);
            trainCourse.setCreateUser(userId);
            trainCourse.setCenterAreaId(centerAreaId);
            return WebResult.okResult(trainCourseService.save(trainCourse));
        } else {
            return WebResult.okResult(trainCourseService.update(trainCourse));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "培训项目课程字典列表")
    @PostMapping(path = "/findAll")
    @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", required = true, paramType = "query")
    public WebResult findAll(@RequestBody String centerAreaId) {
        MyAssert.isNull(centerAreaId, DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(trainCourseService.findAll(JSONObject.parseObject(centerAreaId).getString("centerAreaId")));
    }
    @ApiOperation(value = "分页查询对应的培训课程")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15")
    })
    public WebResult findAllPage(@RequestBody SortVo request){
        valideSort(request.getPage(), request.getPage());
        return WebResult.okResult(trainCourseService.findAllPage(PageRequest.of(request.getPage(), request.getSize())));
    }

//    @ApiOperation(value = "培训项目课程字典列表")
//    @PostMapping(path = "/findById")
//    @ApiImplicitParam(name = "planId", value = "项目计划id", dataType = "string", required = true, paramType = "query")
//    public WebResult findById(@RequestBody String pjPlanId) {
//        MyAssert.isNull(pjPlanId, DefineCode.ERR0010, "项目计划id不为空");
//        return WebResult.okResult(trainCourseService.findId(JSONObject.parseObject(pjPlanId).getString("planId")));
//    }
}