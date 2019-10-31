package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseChapterService;
import com.project.course.service.CourseRecordsService;
import com.project.portal.course.request.ChapterRecordSaveReq;
import com.project.portal.course.request.CourseChapterFindPageAllReq;
import com.project.portal.course.request.CourseChapterRecordFindReq;
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

import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 12:15
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "课程章节学习记录", tags = {"课程章节学习记录管理"})
@RequestMapping(path = "/courseChapterRecord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CourseChapterRecordController {
    private final CourseChapterService courseChapterService;
    private final CourseRecordsService courseRecordsService;
    private final TokenService tokenService;

    public CourseChapterRecordController(CourseChapterService courseChapterService, TokenService tokenService,
                                         CourseRecordsService courseRecordsService) {
        this.courseChapterService = courseChapterService;
        this.courseRecordsService = courseRecordsService;
        this.tokenService = tokenService;
    }

    private void myAssertCourseChapter(String studentId, String courseId, String chapterId) {
        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生id不为空");
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        MyAssert.isNull(chapterId, DefineCode.ERR0010, "章节id不为空");
    }

    private void myAssertSaveVideoRecord(String locationTime, Long duration, Long videoDuration) {
        MyAssert.isNull(locationTime, DefineCode.ERR0010, "观看视频位置不为空");
        MyAssert.isNull(duration, DefineCode.ERR0010, "观看视频时间长度");
        MyAssert.isNull(videoDuration, DefineCode.ERR0010, "视频总长度不为空");
    }

    @UserLoginToken
    @PostMapping(path = "/saveChapterRecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "chapterId", value = "章节Id", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "locationTime", value = "观看视频位置", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "duration", value = "观看视频时间长度", dataType = "int", paramType = "form", required = true),
            @ApiImplicitParam(name = "videoDuration", value = "视频总长度(秒)", dataType = "int", paramType = "form", required = true)
    })
    public WebResult saveChapterRecord(@RequestBody ChapterRecordSaveReq req, HttpServletRequest request) {
        myAssertCourseChapter(req.getStudentId(), req.getCourseId(), req.getChapterId());
        myAssertSaveVideoRecord(req.getLocationTime(), req.getDuration(), req.getVideoDuration());
        String token = request.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        com.project.course.web.req.CourseRecordsSaveReq recordsSaveReq = new com.project.course.web.req.CourseRecordsSaveReq();
        BeanUtil.copyProperties(req, recordsSaveReq);
        recordsSaveReq.setCreateUser(userId);
        recordsSaveReq.setCenterAreaId(centerAreaId);
        courseRecordsService.saveChapterRecord(recordsSaveReq);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询学习课程章节学习记录")
    @PostMapping(path = "/findCourseChapterRecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "string", paramType = "form", required = true),
            @ApiImplicitParam(name = "chapterId", value = "章节Id", dataType = "string", paramType = "form", example = "如果参数为空则查询课程记录")
    })
    public WebResult findCourseChapterRecord(@RequestBody CourseChapterRecordFindReq req){
        MyAssert.isNull(req.getCourseId(), DefineCode.ERR0010, "课程Id不能为空");
        MyAssert.isNull(req.getStudentId(), DefineCode.ERR0010, "学生Id不能为空");
        if (StrUtil.isBlank(req.getChapterId())){
            return WebResult.okResult(courseRecordsService.findCourseRecordsByStudentIdAndCourseId(req.getStudentId(), req.getCourseId()));
        }
        return WebResult.okResult(courseRecordsService.findChapterRecordsByStudentIdAndChapterId(req.getStudentId(), req.getCourseId(), req.getChapterId()));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询学生对应的学习课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string"),
            @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "string"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    @PostMapping(path = "/findCourseCourseIdPageAll")
    public WebResult findCourseCourseIdPageAll(@RequestBody CourseChapterFindPageAllReq req) {
        MyAssert.isNull(req.getStudentId(), DefineCode.ERR0010, "学生Id不为空");
        valideSort(req.getPage(), req.getSize());
        if (StrUtil.isBlank(req.getCourseId())) {
            return WebResult.okResult(courseRecordsService.findCourseByStudentId(req.getStudentId(), req.getPage(), req.getSize()));
        } else {
            return WebResult.okResult(courseRecordsService.findCourseByCourseIdAndStudentId(req.getStudentId(), req.getCourseId(), req.getPage(), req.getSize()));
        }
    }
}