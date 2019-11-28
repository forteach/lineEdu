package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.CourseChapter;
import com.project.course.service.CourseChapterService;
import com.project.portal.course.request.ChapterDataListReq;
import com.project.portal.course.request.CourseChapterIdDeleteReq;
import com.project.portal.course.request.CourseChapterReq;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 15:52
 * @Version: 1.0
 * @Description: 科目章节
 */
@Slf4j
@RestController
@RequestMapping(path = "/courseChapter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "科目章节接口", tags = {"科目章节信息"})
public class CourseChapterController {

    @Resource
    private CourseChapterService courseChapterService;
    @Resource
    private TokenService tokenService;

    @UserLoginToken
    @PostMapping("/save")
    @ApiOperation(value = "保存科目章节", notes = "保存科目章节信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "章节编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseId", value = "科目编号", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "chapterName", value = "章节名称", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "chapterParentId", value = "章节父编号", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "sort", value = "层级位置", defaultValue = "1", required = true, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "chapterType", value = "目录类型", dataType = "int", required = true, paramType = "form"),
            @ApiImplicitParam(name = "publish", value = "是否发布　Y(是) N(否)", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "randomQuestionsNumber", value = "随机题目数量", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "videoTime", value = "需要观看视频长度(秒)", dataType = "int", paramType = "form")
    })
    public WebResult save(@ApiParam(name = "courseChapter", value = "科目章节对象", required = true) @RequestBody CourseChapterReq req, HttpServletRequest request) {
        CourseChapter cs = new CourseChapter();
        BeanUtil.copyProperties(req, cs);
        String token = request.getHeader("token");
        String createUser = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        cs.setCreateUser(createUser);
        cs.setUpdateUser(createUser);
        cs.setCenterAreaId(centerAreaId);
        if (req.getRandomQuestionsNumber() == null){
            cs.setRandomQuestionsNumber(5);
        }
        return WebResult.okResult(courseChapterService.save(cs));
    }

    @UserLoginToken
    @ApiOperation(value = "查询科目章节信息", notes = "根据章节ID 查询对应的信息")
    @PostMapping("/getCourseChapterById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "科目id", required = true, dataType = "string", paramType = "query")
    })
    public WebResult getCourseChapterById(@ApiParam(value = "根据科目ID 查询对应上层科目信息", name = "chapterId", required = true) @RequestBody String chapterId) {
        MyAssert.blank(chapterId, DefineCode.ERR0010, "科目id不为空");
        return WebResult.okResult(courseChapterService.getCourseChapterById(JSONObject.parseObject(chapterId).getString("chapterId")));
    }

    @UserLoginToken
    @PostMapping("/deleteById")
    @ApiOperation(notes = "根据ID删除对应的科目章节信息(物理删除)", value = "删除科目章节信息(物理删除)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "科目id", required = true, dataType = "string", paramType = "query")
    })
    public WebResult deleteById(@RequestBody CourseChapterIdDeleteReq req) {
        MyAssert.isTrue(StrUtil.isBlank(req.getChapterId()), DefineCode.ERR0010, "科目id不为空");
        courseChapterService.deleteById(req.getChapterId());
        return WebResult.okResult();
    }

    @UserLoginToken
    @PostMapping("/deleteIsValidById")
    @ApiOperation(notes = "根据ID删除对应的科目章节信息(逻辑删除)", value = "删除科目章节信息(逻辑删除)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", value = "科目id", required = true, dataType = "string", paramType = "query")
    })
    public WebResult deleteIsValidById(@ApiParam(name = "chapterId", value = "根据章节ID删除对应的信息(逻辑删除)", required = true) @RequestBody String chapterId) {
        MyAssert.blank(chapterId, DefineCode.ERR0010, "章节ID不为空");
        courseChapterService.deleteIsValidById(String.valueOf(JSONObject.parseObject(chapterId).getString("chapterId")));
        return WebResult.okResult();
    }

    /**
     * 根据科目ID 查询章节信息
     *
     * @param courseId
     * @return
     */
    @UserLoginToken
    @PostMapping("/findByCourseId")
    @ApiOperation(value = "查找章节信息", notes = "客户端根据科目ID查询章节目录树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "章节id", required = true, dataType = "string", paramType = "query")
    })
    public WebResult findByCourseId(@ApiParam(name = "courseId", value = "根据章节ID 查询对应上层科目信息", required = true) @RequestBody String courseId) {
        MyAssert.blank(courseId, DefineCode.ERR0010, "科目id不为空");
        return WebResult.okResult(courseChapterService.findByCourseId(JSONObject.parseObject(courseId).getString("courseId")));
    }

    @UserLoginToken
    @ApiOperation(value = "批量保存课程对应的章节信息和视频资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", dataType = "string", value = "课程Id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "chapterParentId", value = "父章节Id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "teacherName", value = "教师名称", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "centerName", value = "学习中心名称", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "files", dataType = "list", dataTypeClass = List.class, value = "课程Id", required = true, paramType = "form")
    })
    @PostMapping(path = "/saveChapterDataList")
    public WebResult saveChapterDataList(@RequestBody ChapterDataListReq req, HttpServletRequest httpServletRequest){
        MyAssert.isNull(req.getCourseId(), DefineCode.ERR0010, "课程Id不能是空");
        MyAssert.isNull(req.getCourseName(), DefineCode.ERR0010, "课程名称不能是空");
        MyAssert.isTrue(req.getFiles().isEmpty(), DefineCode.ERR0010, "资料信息不能是空");
        MyAssert.isNull(req.getChapterParentId(), DefineCode.ERR0010, "父章节Id不能为空");
        MyAssert.isNull(req.getCenterName(), DefineCode.ERR0010, "学习中心名称不能为空");
        MyAssert.isNull(req.getTeacherName(), DefineCode.ERR0010, "教师名称不能为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerId = tokenService.getCenterAreaId(token);
        req.getFiles().forEach(v -> courseChapterService.saveChapterDataList(req.getCourseId(), req.getCourseName(), req.getChapterParentId(), v, req.getTeacherName(), req.getCenterName(), userId, centerId));
        return WebResult.okResult();
    }
}