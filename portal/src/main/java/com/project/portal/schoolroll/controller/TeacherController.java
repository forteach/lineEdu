package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.TeacherSaveUpdateRequest;
import com.project.schoolroll.domain.Teacher;
import com.project.schoolroll.service.TeacherService;
import com.project.token.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:45
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "教师信息管理", tags = {"教师管理"})
@RequestMapping(path = "/teacher", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @UserLoginToken
    @ApiOperation(value = "添加修改教师信息")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "teacherName", value = "教师名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "teacherCode", value = "教师代码", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody TeacherSaveUpdateRequest request) {
        Teacher teacher = new Teacher();
        BeanUtil.copyProperties(request, teacher);
        if (StrUtil.isBlank(request.getTeacherId())) {
            return WebResult.okResult(teacherService.save(teacher));
        } else {
            return WebResult.okResult(teacherService.update(teacher));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "查询有效教师信息集合")
    @GetMapping(path = "/")
    public WebResult findAll() {
        return WebResult.okResult(teacherService.findAll());
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询教师信息")
    @PostMapping(path = "/findAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    public WebResult findAllPage(@RequestBody SortVo sortVo) {
        valideSort(sortVo.getPage(), sortVo.getSize());
        return WebResult.okResult(teacherService.findAllPage(PageRequest.of(sortVo.getPage(), sortVo.getSize())));
    }

    @UserLoginToken
    @ApiOperation(value = "移除教师信息")
    @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form")
    @PostMapping("/removeById")
    public WebResult removeById(@RequestBody String teacherId) {
        MyAssert.isNull(teacherId, DefineCode.ERR0010, "教师id不能为空");
        teacherService.removeByTeacherId(JSONObject.parseObject(teacherId).getString("teacherId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除教师信息")
    @DeleteMapping(path = "/teacherId/{teacherId}")
    @ApiImplicitParam(name = "teacherId", value = "教师id", dataType = "string", required = true, paramType = "form")
    public WebResult deleteByTeacherId(@PathVariable String teacherId) {
        MyAssert.isNull(teacherId, DefineCode.ERR0010, "教师id不能为空");
        teacherService.deleteByTeacherId(JSONObject.parseObject(teacherId).getString("teacherId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "根据教师代码删除教师信息")
    @DeleteMapping(path = "/teacherCode/{teacherCode}")
    public WebResult deleteByTeacherCode(@PathVariable String teacherCode){
        MyAssert.isNull(teacherCode, DefineCode.ERR0010, "教师id不能为空");
        teacherService.deleteByTeacherCode(JSONObject.parseObject(teacherCode).getString("teacherCode"));
        return WebResult.okResult();
    }
}