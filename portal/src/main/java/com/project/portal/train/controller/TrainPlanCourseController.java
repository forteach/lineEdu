package com.project.portal.train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.train.request.TrainPlanCourseSaveUpateRequest;
import com.project.portal.train.vo.TrainPlanCourseVo;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.train.domain.TrainPlanCourse;
import com.project.train.service.TrainPlanCourseService;
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
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:45
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "培训计划课程管理", tags = {"培训计划课程管理"})
@RequestMapping(path = "/trainPlanCourse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TrainPlanCourseController {

    private final TrainPlanCourseService trainPlanCourseService;
    private final TokenService tokenService;

    public TrainPlanCourseController(TrainPlanCourseService trainPlanCourseService, TokenService tokenService) {
        this.trainPlanCourseService = trainPlanCourseService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "保存培训项目修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list", value = "培训计划集合", dataTypeClass = TrainPlanCourseVo.class, paramType = "form"),
            @ApiImplicitParam(name = "courseId", value = "培训课程编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "培训课程名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "teacherName", value = "培训课程教师名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "credit", value = "培训课程学分", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "list", value = "培训计划集合", dataTypeClass = List.class, paramType = "form"),
//            @ApiImplicitParam(name = "planId", value = "培训项目计划编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody TrainPlanCourseSaveUpateRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String centerAreaId = tokenService.getCenterAreaId(token);
        String userId = tokenService.getUserId(token);
        List<TrainPlanCourse> list = request.getList()
                .stream()
                .map(vo -> {
                    TrainPlanCourse trainPlanCourse = new TrainPlanCourse();
                    BeanUtil.copyProperties(vo, trainPlanCourse);
                    trainPlanCourse.setCenterAreaId(request.getCenterAreaId());
                    trainPlanCourse.setUpdateUser(userId);
                    trainPlanCourse.setCreateUser(userId);
                    if (StrUtil.isBlank(request.getCenterAreaId())){
                        trainPlanCourse.setCenterAreaId(centerAreaId);
                    }
                    return trainPlanCourse;
                }).collect(toList());
        return WebResult.okResult(trainPlanCourseService.saveOrUpdate(request.getPjPlanId(), list));
    }

    @UserLoginToken
    @ApiOperation(value = "项目计划ID获取项目课程列表")
    @PostMapping(path = "/findByPjPlanId")
    @ApiImplicitParam(name = "pjPlanId", value = "项目计划id", dataType = "string", required = true, paramType = "query")
    public WebResult findById(@RequestBody String pjPlanId) {
        MyAssert.isNull(pjPlanId, DefineCode.ERR0010, "项目计划id不为空");
        return WebResult.okResult(trainPlanCourseService.findAll(JSONObject.parseObject(pjPlanId).getString("pjPlanId")));
    }
}