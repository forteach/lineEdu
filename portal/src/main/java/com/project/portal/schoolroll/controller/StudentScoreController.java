package com.project.portal.schoolroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.schoolroll.request.OffLineScoreUpdateRequest;
import com.project.portal.schoolroll.request.StudentScoreRequest;
import com.project.portal.teachplan.request.TeachPlanCourseFindAllPageRequest;
import com.project.portal.util.MyExcleUtil;
import com.project.schoolroll.repository.dto.StudentOnLineDto;
import com.project.schoolroll.service.StudentScoreService;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.schoolroll.web.vo.OffLineScoreUpdateVo;
import com.project.schoolroll.web.vo.StudentScorePageAllVo;
import com.project.teachplan.service.TeachService;
import com.project.token.annotation.PassToken;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.portal.request.ValideSortVo.valideSort;
import static com.project.schoolroll.domain.excel.Dic.IMPORT_COURSE_SCORE;

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
    private final StudentOnLineService studentOnLineService;

    public StudentScoreController(StudentScoreService studentScoreService, TeachService teachService,
                                  TokenService tokenService, StudentOnLineService studentOnLineService) {
        this.studentScoreService = studentScoreService;
        this.tokenService = tokenService;
        this.teachService = teachService;
        this.studentOnLineService = studentOnLineService;
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

    @UserLoginToken
    @ApiOperation(value = "更新线下成绩录入")
    @PostMapping(path = "/updateOfflineScore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scoreId", value = "成绩信息id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "offLineScore", value = "线下成绩", dataType = "string", required = true, paramType = "form")
    })
    public WebResult updateOfflineScore(@RequestBody OffLineScoreUpdateRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getScoreId(), DefineCode.ERR0010, "成绩id信息不为空");
        MyAssert.isNull(request.getOffLineScore(), DefineCode.ERR0010, "成绩不为空");
        MyAssert.isFalse(NumberUtil.isNumber(request.getOffLineScore()), DefineCode.ERR0010, "线下成绩不是数字");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        studentScoreService.updateOffLineScore(new OffLineScoreUpdateVo(request.getScoreId(), request.getOffLineScore(), userId));
        return WebResult.okResult();
    }

    @PassToken
    @GetMapping(path = "/exportTemplate/{courseName}")
    @ApiOperation(value = "导出学生成绩导入模板")
    public WebResult exportTemplate(@PathVariable String courseName, HttpServletRequest request, HttpServletResponse response) {
        MyAssert.isTrue(StrUtil.isBlank(courseName), DefineCode.ERR0010, "课程名称不能为空");
        //设置导入数据模板
        List<List<String>> lists = CollUtil.toList(CollUtil.newArrayList("姓名", "身份证号码", courseName));
        MyExcleUtil.getExcel(response, request, lists, courseName + "成绩导入模板.xlsx");
        return WebResult.okResult();
    }


    @PassToken
    @ApiOperation(value = "导入学生线下课程成绩")
    @PostMapping(path = "/importCourseScore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程Id", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", required = true, paramType = "form", dataType = "string"),
//            @ApiImplicitParam(name = "classId", value = "班级id", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "form", dataType = "string")
    })
    public WebResult importCourseScore(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
        MyAssert.isTrue(file.isEmpty(), DefineCode.ERR0010, "文件信息不为空");
        String courseId = httpServletRequest.getParameter("courseId");
        String courseName = httpServletRequest.getParameter("courseName");
        String token = httpServletRequest.getParameter("token");
        String classId = httpServletRequest.getParameter("classId");
        MyAssert.isTrue(StrUtil.isBlank(courseId), DefineCode.ERR0010, "课程Id不为空");
        MyAssert.isTrue(StrUtil.isBlank(token), DefineCode.ERR0010, "token is null");
        MyAssert.isTrue(StrUtil.isBlank(courseName), DefineCode.ERR0010, "课程名称不能为空");
//        MyAssert.isTrue(StrUtil.isBlank(classId), DefineCode.ERR0010, "班级Id不能为空");
        String type = FileUtil.extName(file.getOriginalFilename());
        if (StrUtil.isNotBlank(type) &&
                "xlsx".equals(type) || "xls".equals(type)) {
            String centerId = tokenService.getCenterAreaId(token);
            //判断计划是否结束，只有结束的计划才能上传成绩
            MyAssert.isFalse(teachService.checkPlanDate(classId), DefineCode.ERR0010, "您导入的班级计划还没有结束，暂时不能导入成绩");
            String key = IMPORT_COURSE_SCORE.concat(centerId).concat("$").concat(centerId);
            studentScoreService.checkoutKey(key);
            String userId = tokenService.getUserId(token);
            try {
                studentScoreService.importScore(file.getInputStream(), key, courseId, courseName, userId);
                return WebResult.okResult();
            } catch (IOException e) {
                studentScoreService.deleteKey(key);
                log.error("students in IOException, file : [{}],  message : [{}]", file, e.getMessage());
                e.printStackTrace();
            }
        }
        return WebResult.failException("导入的文件格式不是Excel文件");
    }

    @UserLoginToken
//    @PassToken
    @ApiOperation(value = "导出学生成绩")
    @GetMapping(path = "/exportScore")
    public WebResult exportScore(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiNGJiMTdkODU1MzY2NDllMzkwYTFmNzgyYjA5N2EzNTkiLCJ0ZWFjaGVyIiwiNDk2MWJkYTZiMmIwNGZiNmE0Y2JiNzJmMzU2MWQyZGMiLCIxIl0sImV4cCI6MTU3ODIxMzg3OCwiaWF0IjoxNTc3NjA5MDc4fQ.b2vUGy4O08W6xAZXp2MuOs6lzrGOpS3ANs6ma19ajJs";
        String centerId = tokenService.getCenterAreaId(token);
        MyExcleUtil.getExcel(response, request, studentScoreService.exportScore(centerId), "学生成绩.xlsx");
        return WebResult.okResult();
    }

//    @PassToken
    @UserLoginToken
    @ApiOperation(value = "分页查询计划课程的在线学生学习成绩信息")
    @PostMapping(path = "/importPlanCourseScoreAllPage")
//    @GetMapping(path = "/importPlanCourseScoreAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyName", value = "学生名称", dataType = "string", paramType = "query")
    })
    public WebResult importAllStudyCourseScore(@RequestBody TeachPlanCourseFindAllPageRequest req,
                                               HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String token = httpServletRequest.getHeader("token");
        String centerId = tokenService.getCenterAreaId(token);
        PageRequest of = PageRequest.of(0, 10000);
        List<StudentOnLineDto> onLineDtoList;
        if(tokenService.isAdmin(token)){
            onLineDtoList = studentOnLineService.findStudentOnLineDto(of, req.getStudentName(), TAKE_EFFECT_OPEN, "", req.getGrade(), req.getSpecialtyName(), req.getClassName()).getContent();
        }else {
            onLineDtoList = studentOnLineService.findStudentOnLineDto(of, req.getStudentName(), TAKE_EFFECT_OPEN, centerId, req.getGrade(), req.getSpecialtyName(), req.getClassName()).getContent();
//            onLineDtoList = studentOnLineService.findStudentOnLineDto(of, "", TAKE_EFFECT_OPEN, "", "", "", "").getContent();
        }
        if (!onLineDtoList.isEmpty()){
            //查询学生的信息不为空后导出对应的学生成绩
            List<List<String>> lists = studentScoreService.exportScore(onLineDtoList);
            MyExcleUtil.getExcel(httpServletResponse, httpServletRequest, lists, "学生成绩.xlsx");
        }
        MyAssert.isTrue(onLineDtoList.isEmpty(), DefineCode.ERR0010, "不存在要导出的数据");
        return WebResult.okResult();
    }
}