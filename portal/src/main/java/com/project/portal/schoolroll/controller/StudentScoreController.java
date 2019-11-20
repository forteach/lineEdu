package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.ScoreFindAllPageRequest;
import com.project.portal.schoolroll.request.StudentScoreRequest;
import com.project.portal.util.MyExcleUtil;
import com.project.schoolroll.service.StudentScoreService;
import com.project.schoolroll.web.vo.PlanStudentVo;
import com.project.schoolroll.web.vo.StudentScorePageAllVo;
import com.project.teachplan.service.TeachService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.project.portal.request.ValideSortVo.valideSort;
import static java.util.stream.Collectors.toList;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:44
 * @version: 1.0
 * @description: 学生成绩controller
 */
@Slf4j
@RestController
@RequestMapping(path = "/studentScore", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "学生成绩管理", tags = {"学生成绩管理"})
public class StudentScoreController {
    private final StudentScoreService studentScoreService;
    private final TokenService tokenService;
    private final TeachService teachService;

    public StudentScoreController(StudentScoreService studentScoreService,
                                  TeachService teachService,
                                  TokenService tokenService) {
        this.studentScoreService = studentScoreService;
        this.tokenService = tokenService;
        this.teachService = teachService;
    }

    @UserLoginToken
    @ApiOperation(value = "查询学生对应成绩信息")
    @PostMapping(path = "/findStudentScore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生Id", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "学期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "courseType", value = "课程类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "schoolYear", value = "学年", dataType = "string", paramType = "query"),
    })
    public WebResult findStudentScore(@RequestBody StudentScoreRequest request) {
        MyAssert.isNull(request.getStudentId(), DefineCode.ERR0010, "学生id不为空");
        if (StrUtil.isBlank(request.getCourseId())) {
            return WebResult.okResult(studentScoreService.findByStudentId(request.getStudentId()));
        } else {
            return WebResult.okResult(studentScoreService.findByStudentIdAndCourseId(request.getStudentId(), request.getCourseId()));
        }
    }

    @UserLoginToken
    @ApiOperation(value = "删除无效的学生成绩信息")
    @DeleteMapping(path = "/deleteByScoreId")
    @ApiImplicitParam(name = "scoreId", value = "成绩信息id", dataType = "string", required = true, paramType = "form")
    public WebResult deleteStudentScoreById(@RequestBody String scoreId) {
        MyAssert.isNull(scoreId, DefineCode.ERR0010, "成绩id信息不为空");
        studentScoreService.deleteStudentScoreById(JSONObject.parseObject(scoreId).getString("scoreId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询学生对应成绩信息", tags = {"多条件分页查询学生成绩信息"})
    @PostMapping(path = "/findStudentScorePageAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "学期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "courseType", value = "课程类别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "schoolYear", value = "学年", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", required = true, paramType = "query")
    })
    public WebResult findStudentScorePageAll(@RequestBody StudentScoreRequest request) {
        valideSort(request.getPage(), request.getSize());
        StudentScorePageAllVo pageAllVo = new StudentScorePageAllVo();
        BeanUtil.copyProperties(request, pageAllVo);
        return WebResult.okResult(studentScoreService.findStudentScorePageAll(pageAllVo, PageRequest.of(request.getPage(), request.getSize())));
    }

//    @UserLoginToken
//    @ApiOperation(value = "更新线下成绩录入")
//    @PostMapping(path = "/updateOfflineScore")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "scoreId", value = "成绩信息id", dataType = "string", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "offLineScore", value = "线下成绩", dataType = "string", required = true, paramType = "form")
//    })
//    public WebResult updateOfflineScore(@RequestBody OffLineScoreUpdateRequest request, HttpServletRequest httpServletRequest) {
//        MyAssert.isNull(request.getScoreId(), DefineCode.ERR0010, "成绩id信息不为空");
//        MyAssert.isNull(request.getOffLineScore(), DefineCode.ERR0010, "成绩不为空");
//        MyAssert.isFalse(NumberUtil.isNumber(request.getOffLineScore()), DefineCode.ERR0010, "线下成绩不是数字");
//        String token = httpServletRequest.getHeader("token");
//        String userId = tokenService.getUserId(token);
//        studentScoreService.updateOffLineScore(new OffLineScoreUpdateVo(request.getScoreId(), request.getOffLineScore(), userId));
//        return WebResult.okResult();
//    }

    @UserLoginToken
    @ApiOperation(value = "导出学生成绩")
    @GetMapping(path = "/exportScore")
    public WebResult exportScore(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        String centerId = tokenService.getCenterAreaId(token);
        MyExcleUtil.getExcel(response, request, studentScoreService.exportScore(centerId), "学生成绩.xlsx");
        return WebResult.okResult();
    }
}