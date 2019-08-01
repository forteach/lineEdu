package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.course.domain.Course;
import com.project.course.service.CourseService;
import com.project.course.service.CourseShareService;
import com.project.course.web.resp.CourseResp;
import com.project.course.web.resp.CourseSaveResp;
import com.project.course.web.vo.RCourse;
import com.project.databank.web.vo.DataDatumVo;
import com.project.portal.course.controller.verify.CourseVer;
import com.project.portal.course.request.CourseFindAllReq;
import com.project.portal.course.request.CourseImagesReq;
import com.project.portal.course.request.CourseSaveReq;
import com.project.portal.course.request.CourseStudyReq;
import com.project.portal.course.response.CourseListResp;
import com.project.portal.course.response.CourseUsersResp;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.project.portal.request.ValideSortVo.valideSort;
import static java.util.stream.Collectors.toList;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 16:52
 * @Version: 1.0
 * @Description:　科目课程信息操作
 */
@Slf4j
@RestController
@RequestMapping(path = "/course", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "课程科目操作", tags = {"课程科目操作相关信息"})
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private TokenService tokenService;

    @Resource
    private CourseShareService courseShareService;

    @UserLoginToken
    @ApiOperation(value = "保存课程科目信息", notes = "保存科目课程信息")
    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course", value = "科目课程对象", dataTypeClass = Course.class, required = true),
            @ApiImplicitParam(name = "oldShareId", value = "编辑修改前条记录的分享编号", dataTypeClass = String.class),
//            @ApiImplicitParam(name = "teachers", value = "教师信息列表", dataTypeClass = Teacher.class)
    })
    public WebResult save(@ApiParam(name = "courseReq", value = "科目课程对象") @RequestBody CourseSaveReq req, HttpServletRequest request) {
        if ("2".equals(req.getCourse().getLessonPreparationType())) {
            MyAssert.elt(0, req.getTeachers().size(), DefineCode.ERR0010, "教师信息列表不为空");
        }
        //验证请求信息
        CourseVer.saveValide(req);
        //设置service数据
        RCourse rcourse = req.getCourse();
        Course course = new Course();
        UpdateUtil.copyNullProperties(rcourse, course);
        //教辅设置授课类型是线下课堂
        course.setTeachingType("3");
        course.setCreateUser(tokenService.getUserId(request.getHeader("token")));
        List<String> list = courseService.save(course, req.getTeachers());
        //创建输出课程对象
        CourseSaveResp courseSaveResp = CourseSaveResp.builder()
                .courseId(list.get(0))
                .shareId(list.get(1))
                .build();
        return WebResult.okResult(courseSaveResp);
    }

    @UserLoginToken
    @ApiOperation(value = "修改科目课程信息", notes = "修改科目信息")
    @PostMapping("/edit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course", value = "科目课程对象", dataTypeClass = Course.class, required = true),
            @ApiImplicitParam(name = "oldShareId", value = "修改前课程的备课共享编号", dataTypeClass = String.class),
//            @ApiImplicitParam(name = "teachers", value = "教师信息列表", dataTypeClass = Teacher.class)
    })
    public WebResult edit(@ApiParam(name = "courseReq", value = "科目课程对象", required = true) @RequestBody CourseSaveReq courseReq) {
        if ("2".equals(courseReq.getCourse().getLessonPreparationType())) {
            MyAssert.elt(0, courseReq.getTeachers().size(), DefineCode.ERR0010, "教师信息列表不为空");
        }
        RCourse rcourse = courseReq.getCourse();
        Course course = new Course();
        BeanUtil.copyProperties(rcourse, course);
        String courseId = course.getCourseId();
        Course source = courseService.getById(courseId);
        BeanUtil.copyProperties(source, course);
        course.setCreateTime(source.getCreateTime());
        //创建输出课程对象
        CourseSaveResp courseSaveResp = CourseSaveResp.builder()
                .courseId(course.getCourseId())
                .shareId(courseService.edit(course, courseReq.getOldShareId(), courseReq.getTeachers()))
                .build();
        return WebResult.okResult(courseSaveResp);
    }


    @UserLoginToken
    @PostMapping("/getCourse")
    @ApiOperation(value = "获取科目课程信息", notes = "根据科目课程ID查询科目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true, example = "{\"courseId\":\"2c918099676317d0016763e051f50000\"}")
    })
    public WebResult getCourseByCourseId(@ApiParam(name = "courseId", value = "根据科目ID 查询对应科目信息", type = "string", required = true, example = "{\"courseId\":\"2c918099676317d0016763e051f50000\"}")
                                         @RequestBody String courseId) {
        MyAssert.blank(courseId, DefineCode.ERR0010, "科目ID不为空");
        Map<String, Object> result = courseService.getCourseById(String.valueOf(JSONObject.parseObject(courseId).get("courseId")));
        Course course = (Course) result.get("course");
        String shareId = result.get("shareId").toString();
        CourseResp reps = new CourseResp(course.getCourseId(),
                course.getCourseName(),
                course.getCourseNumber(),
                course.getLessonPreparationType(),
                course.getTeachingType(),
                course.getTopPicSrc(),
                course.getShareType(),
                course.getCourseDescribe(),
                shareId,
                course.getAlias());
        return WebResult.okResult(reps);
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询", notes = "分页查询分页科目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortVo", value = "分页参数息", dataTypeClass = CourseFindAllReq.class, example = "{\"sortVo\":{\"isValidated\":\"0\",\"page\":0,\"size\":15,\"sort\":1}}")
    })
    @PostMapping("/findAll")
    public WebResult findAll(@ApiParam(name = "sortVo", value = "分页查科目信息") @RequestBody CourseFindAllReq req, HttpServletRequest request) {
        valideSort(req.getPage(), req.getSize());
        req.setUserId(tokenService.getTeacherId(request.getHeader("token")));
        PageRequest page = PageRequest.of(req.getPage(), req.getSize());
        return WebResult.okResult(courseService.findAll(page).stream()
                .map((item) -> {
                    return new CourseListResp(item.getCourseId(), item.getCourseName(), item.getCourseNumber(), item.getLessonPreparationType(), item.getTopPicSrc(), item.getAlias());
                })
                .collect(toList()));
    }


    @UserLoginToken
    @PostMapping("/findMyCourse")
    @ApiOperation(value = "查询我的课程", notes = "教师端分页查询我的课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", paramType = "query")
    })
    public WebResult findMyCourse(@ApiParam(name = "CourseFindAllReq", value = "课程列表请求对象", required = true) @RequestBody CourseFindAllReq req, HttpServletRequest request) {
        String userId = tokenService.getUserId(request.getHeader("token"));
        valideSort(req.getPage(), req.getSize());
        PageRequest page = PageRequest.of(req.getPage(), req.getSize());
        return WebResult.okResult(courseService.findMyCourse(userId, page).stream()
                .map((item) -> {
                    return new CourseListResp(item.getCourseId(), item.getCourseName(), item.getCourseNumber(), item.getLessonPreparationType(), item.getTopPicSrc(), item.getAlias());
                })
                .collect(toList()));
    }

    @UserLoginToken
    @PostMapping("/selectTeachersByShareId")
    @ApiOperation(value = "根据课程备课分享ID查询对应的协作老师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shareId", value = "科目备课分享ID", dataType = "string", required = true)
    })
    public WebResult selectTeachersByCourseId(@ApiParam(name = "shareId", value = "查询对应的协作老师信息", type = "string", required = true) @RequestBody String shareId) {
        MyAssert.blank(shareId, DefineCode.ERR0010, "科目备课分享ID不为空");
        List<CourseUsersResp> list = courseShareService.findByShareIdUsers(String.valueOf(JSONObject.parseObject(shareId).getString("shareId")))
                .stream().map(item -> {
                    CourseUsersResp resp = new CourseUsersResp();
                    BeanUtil.copyProperties(item, resp);
                    return resp;
                }).collect(toList());

        return WebResult.okResult(list);
    }

//    @UserLoginToken
//    @ApiOperation(value = "学生查询我的课程信息", notes = "学生端查询我的课程信息")
//    @GetMapping(path = "/myCourseList")
//    public WebResult myCourseList(HttpServletRequest request){
//        String classId = tokenService.getClassId(request);
//        return WebResult.okResult(courseService.myCourseList(classId));
//    }

    @UserLoginToken
    @ApiOperation(value = "查询课程信息", notes = "查询同步的外部课程信息")
    @GetMapping("/findCourseList")
    public WebResult findCourseList() {
        return WebResult.okResult(courseService.findCourseList());
    }


    //******************************************************************************************************一下内容未修改

    /**
     * 通过文件资源 ID 逻辑删除文件资源信息
     *
     * @param courseId
     * @return
     */
    @UserLoginToken
    @ApiOperation(value = "使其无效", notes = "删除科目信息(逻辑删除)")
    @PostMapping("/deleteIsValidById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true)
    })
    public WebResult deleteIsValidById(@ApiParam(name = "courseId", value = "根据资源ID 逻辑删除对应科目信息", type = "string", required = true) @RequestBody String courseId) {
        MyAssert.blank(courseId, DefineCode.ERR0010, "科目ID不为空");
        courseService.deleteIsValidById(String.valueOf(JSONObject.parseObject("courseId").getString("courseId")));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除科目信息", notes = "删除科目对象 (物理删除)")
    @PostMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course", value = "科目课程对象", dataTypeClass = Course.class, dataType = "string", required = true),
//            @ApiImplicitParam(name = "teachers", value = "教师信息列表", dataTypeClass = Teacher.class)
    })
    public WebResult delete(@ApiParam(name = "course", value = "科目对象", required = true) @RequestBody Course course) {
        MyAssert.blank(course.getCourseId(), DefineCode.ERR0010, "科目ID不为空");
        courseService.delete(course);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除文件信息", notes = "根据文件资源ID 删除科目信息")
    @PostMapping("/deleteById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true)
    })
    public WebResult deleteById(@ApiParam(name = "courseId", value = "根据科目ID 删除对应科目信息", type = "string", required = true) @RequestBody String courseId) {
        MyAssert.blank(courseId, DefineCode.ERR0010, "科目ID不为空");
        courseService.deleteById(String.valueOf(JSONObject.parseObject(courseId).get("courseId")));
        return WebResult.okResult();
    }

    /**
     * 保存课程轮播图信息
     *
     * @param courseImagesReq
     * @return
     */
    @UserLoginToken
    @PostMapping("/saveCourseImages")
    @ApiOperation(value = "保存课程科目轮播图", notes = "保存科目的轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "images", value = "图册信息数组", dataTypeClass = DataDatumVo.class, required = true)
    })
    public WebResult saveCourseImages(@ApiParam(value = "课程ID和图片", name = "courseImagesReq") @RequestBody CourseImagesReq courseImagesReq) {
        MyAssert.blank(courseImagesReq.getCourseId(), DefineCode.ERR0010, "科目ID不为空");
        com.project.course.web.req.CourseImagesReq imagesReq = new com.project.course.web.req.CourseImagesReq();
        BeanUtil.copyProperties(courseImagesReq, imagesReq);
        courseService.saveCourseImages(imagesReq);
        return WebResult.okResult();
    }

    /**
     * 查询轮播图信息
     *
     * @param courseId
     * @return
     */
    @UserLoginToken
    @PostMapping("/findImagesByCourseId")
    @ApiOperation(value = "查询课程轮播图", notes = "根据课程科目ID查询对应的轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true, example = "ff808181677d238701677d26fdae0002")
    })
    public WebResult findImagesByCourseId(@ApiParam(name = "courseId", value = "查询对应的", type = "string", required = true) @RequestBody String courseId) {
        MyAssert.blank(courseId, DefineCode.ERR0010, "科目ID不为空");
        return WebResult.okResult(courseService.findImagesByCourseId(String.valueOf(JSONObject.parseObject(courseId).getString("courseId"))));
    }

    /**
     * 查询学生的课程学生信息
     *
     * @param courseStudyReq
     * @param request
     * @return
     */
    @UserLoginToken
    @ApiOperation(value = "查询学生课程学习状态信息", notes = "查询相关课程的学生状态信息")
    @PostMapping("/findCourseStudy")
    @ApiImplicitParam(name = "studyStatus", value = "学习状态 0 未学习　1 在学习　2 已完结", dataType = "int")
    public WebResult findCourseStudy(@RequestBody CourseStudyReq courseStudyReq, HttpServletRequest request) {
        String studentId = tokenService.getStudentId(request.getHeader("token"));
        return WebResult.okResult(courseService.findCourseStudyList(studentId, courseStudyReq.getStudyStatus()));
    }

    @UserLoginToken
    @ApiOperation(value = "删除课程轮播图")
    @ApiImplicitParam(name = "courseId", value = "科目/课程id", dataType = "string", required = true, paramType = "form")
    @PostMapping(path = "/deleteImagesByCourseId")
    public WebResult deleteImagesByCourseId(@RequestBody String courseId) {
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        return WebResult.okResult(courseService.deleteImagesByCourseId(String.valueOf(JSONObject.parseObject(courseId).getString("courseId"))));
    }
}