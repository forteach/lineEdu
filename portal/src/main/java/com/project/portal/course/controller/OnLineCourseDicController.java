package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.OnLineCourseDic;
import com.project.course.service.OnLineCourseDicService;
import com.project.portal.course.request.CourseDicfindAllPageReq;
import com.project.portal.course.request.OnLineCourseDicSaveUpdateRequest;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 11:40
 * @Version: 1.0
 * @Description:
 */
@Slf4j
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
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "课程类型　1.线上，2.线下,3.混合", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody OnLineCourseDicSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        OnLineCourseDic onLineCourseDic = new OnLineCourseDic();
        BeanUtil.copyProperties(request, onLineCourseDic);
        String token = httpServletRequest.getHeader("token");
        String centerId = tokenService.getCenterAreaId(token);
        String userId = tokenService.getUserId(token);
        onLineCourseDic.setCreateUser(userId);
        onLineCourseDic.setUpdateUser(userId);
        onLineCourseDic.setCenterAreaId(centerId);
        if (StrUtil.isBlank(request.getCourseId())) {
            MyAssert.isNull(request.getCourseName(), DefineCode.ERR0010, "课程名称不为空");
            MyAssert.isNull(request.getType(), DefineCode.ERR0010, "课程类型不为空");
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
    public WebResult findAll(HttpServletRequest httpServletRequest) {
        String centerId = tokenService.getCenterAreaId(httpServletRequest.getHeader("token"));
        if (StrUtil.isBlank(centerId)) {
            return WebResult.okResult(onLineCourseDicService.findAll());
        }
        return WebResult.okResult(onLineCourseDicService.findAllByCenterAreaId(centerId));
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
        String courseIdStr = JSONObject.parseObject(courseId).getString("courseId");
        log.info("delete course dic courseId : [{}]", courseIdStr);
        onLineCourseDicService.deleteByCourseId(courseIdStr);
        return WebResult.okResult();
    }

    @UserLoginToken
    @PutMapping(path = "/status/{courseId}")
    @ApiOperation(value = "修改课程字典状态")
    @ApiImplicitParam(name = "courseId", dataType = "string", required = true, paramType = "form")
    public WebResult updateStatus(@PathVariable String courseId, HttpServletRequest httpServletRequest){
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        onLineCourseDicService.updateStatus(courseId, userId);
        return WebResult.okResult();
    }

    @ApiOperation(value = "分页查询全部课程字典")
    @UserLoginToken
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", required = true, example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", required = true, example = "15", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "课程类型　1.线上，2.线下,3.混合", dataType = "string", example = "1", paramType = "query"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataType = "string", paramType = "query")
    })
    @PostMapping(path = "/findAllPage")
    public WebResult findAllPage(@RequestBody CourseDicfindAllPageReq req){
        valideSort(req.getPage(), req.getSize());
        PageRequest of = PageRequest.of(req.getPage(), req.getSize());
        if (StrUtil.isBlank(req.getType())){
            return WebResult.okResult(onLineCourseDicService.findAllPage(of, req.getCourseName()));
        }else {
            return WebResult.okResult(onLineCourseDicService.findAllPageByType(of, req.getType(), req.getCourseName()));
        }
    }
}