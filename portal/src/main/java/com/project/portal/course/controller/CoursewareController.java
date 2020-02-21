package com.project.portal.course.controller;


import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseRecordsService;
import com.project.course.service.CoursewareService;
import com.project.course.web.req.CourseRecordsSaveReq;
import com.project.course.web.req.CoursewareAll;
import com.project.course.web.req.ImpCoursewareAll;
import com.project.portal.course.request.ChapterVideoReq;
import com.project.portal.response.WebResult;
import com.project.schoolroll.service.LearnCenterService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.user.service.TeacherService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 16:52
 * @Version: 1.0
 * @Description:　科目课程重要课件信息
 */
@Slf4j
@RestController
@RequestMapping(path = "/courseware", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "课程科目操作", tags = {"课程科目重要课件信息"})
public class CoursewareController {

    @Resource
    private CoursewareService coursewareService;
    @Resource
    private TokenService tokenService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private LearnCenterService learnCenterService;
    @Resource
    private CourseRecordsService courseRecordsService;

    @UserLoginToken
    @ApiOperation(value = "保存课程科目信息", notes = "保存科目课程信息")
    @PostMapping("/savefile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "courseId", value = "课程编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "fileUrl", value = "视频url", dataTypeClass = CoursewareAll.class, required = true),
            @ApiImplicitParam(name = "fileName", value = "视频名称", dataType = "string", required = true),
            @ApiImplicitParam(name = "videoTime", value = "文件时长(单位秒)", dataType = "int", required = true)
    })
    public WebResult save(@ApiParam(name = "courseReq", value = "科目课程对象") @RequestBody ImpCoursewareAll req, HttpServletRequest request) {
        MyAssert.blank(req.getCourseId(), DefineCode.ERR0010, "课程编号不为空");
        MyAssert.blank(req.getCourseName(), DefineCode.ERR0010, "课程名称不为空");
        MyAssert.blank(req.getChapterId(), DefineCode.ERR0010, "章节编号不为空");
        MyAssert.blank(req.getFileUrl(), DefineCode.ERR0010, "视频URL不为空");
        MyAssert.blank(req.getFileName(), DefineCode.ERR0010, "视频名称不为空");
        MyAssert.isNull(req.getVideoTime(), DefineCode.ERR0010, "视频时长不为空");
        String token = request.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerId = tokenService.getCenterAreaId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        String centerName = learnCenterService.findByCenterId(centerAreaId).getCenterName();
//        String teacherName = teacherService.findById(userId).getTeacherName();
//        req.setTeacherId(userId);
//        req.setTeacherName(teacherName);
        req.setCreateUser(userId);
        req.setCenterName(centerName);
        coursewareService.saveFile(req, centerId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除视频信息(物理删除)", notes = "删除课件视频信息(物理删除)")
    @PostMapping("/deleteCourseChapterId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
    })
    public WebResult deleteCourseChapterId(@ApiParam(name = "chapterId", value = "章节编号", required = true) @RequestBody String chapterId) {
        MyAssert.blank(chapterId, DefineCode.ERR0010, "章节编号不为空");
        coursewareService.deleteCourseChapterId(JSONObject.parseObject(chapterId).getString("chapterId"));
        return WebResult.okResult();
    }

//    @UserLoginToken
//    @ApiOperation(value = "删除单个课件视频文件(物理删除)")
//    @DeleteMapping("/{fileId}")
//    @ApiImplicitParam(name = "fileId", value = "文件Id", dataType = "string", required = true)
//    public WebResult deleteByFileId(@PathVariable String fileId){
//        MyAssert.isNull(fileId, DefineCode.ERR0010, "文件Id不能为空");
//        coursewareService.deleteByFileId(fileId);
//        return WebResult.okResult();
//    }

    @UserLoginToken
    @ApiOperation(value = "查询章节对应教学视频")
    @PostMapping("/findByChapterId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataTypeClass = String.class),
            @ApiImplicitParam(name = "courseId", value = "课程编号", dataTypeClass = String.class)
    })
    public WebResult findByChapterId(@RequestBody ChapterVideoReq req, HttpServletRequest httpServletRequest) {
        MyAssert.blank(req.getChapterId(), DefineCode.ERR0010, "章节编号不为空");
        String token = httpServletRequest.getHeader("token");
        if (tokenService.isStudent(token)) {
            MyAssert.isNull(req.getChapterId(), DefineCode.ERR0010, "课程编号不为空");
            MyAssert.isNull(req.getCourseName(), DefineCode.ERR0010, "课程名称不为空");
            String studentId = tokenService.getStudentId(token);
            String centerAreaId = tokenService.getCenterAreaId(token);
            //添加学习记录
            courseRecordsService.saveCourseRecord(new CourseRecordsSaveReq(studentId, req.getCourseId(), req.getChapterId(), centerAreaId, studentId, req.getCourseName()));
            return WebResult.okResult(coursewareService.findByChapterIdAndVerifyStatus(req.getCourseId(), req.getChapterId(), studentId));
        } else {
            return WebResult.okResult(coursewareService.findByChapterId(req.getChapterId()));
        }
    }
}