package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.OnLineCourseDicSaveUpdateRequest;
import com.project.schoolroll.domain.online.OnLineCourseDic;
import com.project.schoolroll.service.online.OnLineCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class OnLineCourseController {
    private final OnLineCourseService onLineCourseService;

    public OnLineCourseController(OnLineCourseService onLineCourseService) {
        this.onLineCourseService = onLineCourseService;
    }

    @ApiOperation(value = "在线项目课程字典保存修改")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerAreaId", value = "学习中心id", dataType = "string", paramType = "form")
    })
    public WebResult saveOrUpdate(@RequestBody OnLineCourseDicSaveUpdateRequest request) {
        OnLineCourseDic onLineCourseDic = new OnLineCourseDic();
        BeanUtil.copyProperties(request, onLineCourseDic);
        if (StrUtil.isBlank(request.getCourseId())) {
            return WebResult.okResult(onLineCourseService.save(onLineCourseDic));
        } else {
            return WebResult.okResult(onLineCourseService.update(onLineCourseDic));
        }
    }

    @ApiOperation(value = "培训项目课程字典列表")
    @PostMapping(path = "/findAll")
    @ApiImplicitParam(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string", example = "没有此参数查询全部", paramType = "query")
    public WebResult findAll(@RequestBody String centerAreaId) {
        if (StrUtil.isNotBlank(centerAreaId)){
            return WebResult.okResult(onLineCourseService.findAllByCenterAreaId(JSONObject.parseObject(centerAreaId).getString("centerAreaId")));
        }
        return WebResult.okResult(onLineCourseService.findAll());
    }

    @PostMapping(path = "/removeByCourseId")
    @ApiOperation(value = "移除课程字典中的课程信息")
    @ApiImplicitParam(name = "courseId", dataType = "string", required = true, paramType = "form")
    public WebResult removeByCourseId(@RequestBody String courseId){
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        onLineCourseService.removeByCourseId(JSONObject.parseObject(courseId).getString("courseId"));
        return WebResult.okResult();
    }

    @PostMapping(path = "/deleteByCourseId")
    @ApiOperation(value = "删除课程字典中的课程信息")
    @ApiImplicitParam(name = "courseId", dataType = "string", required = true, paramType = "form")
    public WebResult deleteByCourseId(@RequestBody String courseId){
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        onLineCourseService.deleteByCourseId(JSONObject.parseObject(courseId).getString("courseId"));
        return WebResult.okResult();
    }
}
