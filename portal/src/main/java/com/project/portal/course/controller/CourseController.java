package com.project.portal.course.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.course.domain.Course;
import com.project.course.service.CourseChapterService;
import com.project.course.service.CourseService;
import com.project.course.service.CoursewareService;
import com.project.course.web.resp.CourseSaveResp;
import com.project.course.web.vo.CourseTeacherVo;
import com.project.course.web.vo.CourseVo;
import com.project.databank.service.CourseVerifyVoService;
import com.project.databank.web.vo.DataDatumVo;
import com.project.mongodb.domain.UserRecord;
import com.project.mongodb.service.UserRecordService;
import com.project.portal.course.controller.verify.CourseVer;
import com.project.portal.course.request.*;
import com.project.portal.course.response.CourseListResp;
import com.project.portal.course.vo.RCourse;
import com.project.portal.response.WebResult;
import com.project.schoolroll.service.LearnCenterService;
import com.project.teachplan.service.TeachPlanCourseService;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_AGREE;
import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;
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
    private TeachPlanCourseService teachPlanCourseService;
    @Resource
    private CoursewareService coursewareService;
    @Resource
    private CourseChapterService courseChapterService;
    @Resource
    private CourseVerifyVoService courseVerifyVoService;
    @Resource
    private UserRecordService userRecordService;
    @Resource
    private LearnCenterService learnCenterService;

    @UserLoginToken
    @ApiOperation(value = "保存课程科目信息", notes = "保存科目课程信息")
    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目编号ID", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "科目名称", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "courseNumber", value = "课程字典编号", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "topPicSrc", value = "封面图片路径", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "courseDescribe", value = "课程描述富文本", example = "<p>富文本</p>", paramType = "form"),
            @ApiImplicitParam(name = "alias", value = "别名", dataType = "string", example = "第一学期", paramType = "form"),
            @ApiImplicitParam(name = "isRequired", value = "是否必修课 Y/N", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "learningTime", value = "需要学习的总时长(小时)", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "videoPercentage", value = "观看视频占百分比", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "jobsPercentage", value = "平时作业占百分比", dataType = "string", paramType = "form"),
    })
    public WebResult save(@RequestBody RCourse req, HttpServletRequest request) {
        //验证请求信息
        CourseVer.saveValide(req);
        //设置service数据
        Course course = new Course();
        UpdateUtil.copyNullProperties(req, course);
        String token = request.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerId = tokenService.getCenterAreaId(token);
        course.setCreateUser(userId);
        course.setUpdateUser(userId);
        course.setCenterAreaId(centerId);
        String courseId = courseService.saveUpdate(course);
        //创建输出课程对象
        CourseSaveResp courseSaveResp = CourseSaveResp.builder().courseId(courseId).build();
        return WebResult.okResult(courseSaveResp);
    }


    @UserLoginToken
    @PostMapping("/getCourse")
    @ApiOperation(value = "获取科目课程信息", notes = "根据科目课程ID查询科目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true)
    })
    public WebResult getCourseByCourseId(@ApiParam(name = "courseId", value = "根据科目ID 查询对应科目信息", type = "string", required = true) @RequestBody String courseId) {
        MyAssert.blank(courseId, DefineCode.ERR0010, "科目ID不为空");
        return WebResult.okResult(courseService.getById(JSONObject.parseObject(courseId).getString("courseId")));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询", notes = "分页查询分页科目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    @PostMapping("/findAll")
    public WebResult findAll(@ApiParam(name = "sortVo", value = "分页查科目信息") @RequestBody CourseFindAllReq req, HttpServletRequest request) {
        valideSort(req.getPage(), req.getSize());
        req.setUserId(tokenService.getTeacherId(request.getHeader("token")));
        PageRequest page = PageRequest.of(req.getPage(), req.getSize());
        return WebResult.okResult(courseService.findAll(page).stream()
                .map(item -> new CourseListResp(item.getCourseId(), item.getCourseName(), item.getTopPicSrc(), item.getAlias(), item.getIsValidated()))
                .collect(toList()));
    }


    @UserLoginToken
    @PostMapping("/findMyCourse")
    @ApiOperation(value = "查询我的课程", notes = "教师端分页查询我的课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findMyCourse(@RequestBody CourseFindAllReq req, HttpServletRequest request) {
        valideSort(req.getPage(), req.getSize());
        String userId = tokenService.getUserId(request.getHeader("token"));
        PageRequest page = PageRequest.of(req.getPage(), req.getSize());
        return WebResult.okResult(courseService.findMyCourse(userId, page).stream()
                .map(item -> new CourseListResp(item.getCourseId(), item.getCourseName(), item.getTopPicSrc(), item.getAlias(), item.getIsValidated()))
                .collect(toList()));
    }

    @UserLoginToken
    @ApiOperation(value = "学生查询我的课程信息", notes = "学生端查询我的课程信息")
    @GetMapping(path = "/myCourseList")
    public WebResult myCourseList(HttpServletRequest request) {
        String classId = tokenService.getClassId(request.getHeader("token"));
        return WebResult.okResult(courseService.myCourseList(classId));
    }

    /**
     * 通过文件资源 ID 逻辑删除文件资源信息
     *
     * @return
     */
    @UserLoginToken
    @ApiOperation(value = "修改课程状态", notes = "修改课程课程信息")
    @PostMapping("/updateStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "string", required = true)
    })
    public WebResult updateIsValidById(@RequestBody CourseStatusReq req) {
        MyAssert.blank(req.getCourseId(), DefineCode.ERR0010, "科目ID不为空");
        MyAssert.blank(req.getUserId(), DefineCode.ERR0010, "用户ID不为空");
        courseService.updateStatusById(req.getCourseId(), req.getUserId());
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
    public WebResult saveCourseImages(@ApiParam(value = "课程ID和图片", name = "courseImagesReq") @RequestBody CourseImagesReq courseImagesReq,
                                      HttpServletRequest httpServletRequest) {
        MyAssert.blank(courseImagesReq.getCourseId(), DefineCode.ERR0010, "科目ID不为空");
        com.project.course.web.req.CourseImagesReq imagesReq = new com.project.course.web.req.CourseImagesReq();
        BeanUtil.copyProperties(courseImagesReq, imagesReq);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        imagesReq.setCenterAreaId(centerAreaId);
        imagesReq.setCreateUser(userId);
        courseService.saveCourseImages(imagesReq);
        return WebResult.okResult();
    }

    /**
     * 查询轮播图信息
     *
     * @param req
     * @return
     */
    @UserLoginToken
    @PostMapping("/findImagesByCourseId")
    @ApiOperation(value = "查询课程轮播图", notes = "根据课程科目ID查询对应的轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目ID", dataType = "string", required = true, paramType = "query")
    })
    public WebResult findImagesByCourseId(@RequestBody CourseImageFindReq req, HttpServletRequest httpServletRequest) {
        MyAssert.blank(req.getCourseId(), DefineCode.ERR0010, "科目ID不为空");
        String token = httpServletRequest.getHeader("token");
        if (tokenService.isAdmin(token)) {
            return WebResult.okResult(courseService.findImagesByCourseId(req.getCourseId(), VERIFY_STATUS_APPLY));
        } else {
            return WebResult.okResult(courseService.findImagesByCourseId(req.getCourseId(), VERIFY_STATUS_AGREE));
        }
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
        MyAssert.isNull(courseStudyReq.getStudyStatus(), DefineCode.ERR0010, "学习状态不能为空");
        String studentId = tokenService.getStudentId(request.getHeader("token"));
        return WebResult.okResult(courseService.findCourseStudyList(studentId, courseStudyReq.getStudyStatus()));
    }

    @UserLoginToken
    @ApiOperation(value = "查询课程对应的学生上课信息")
    @PostMapping(path = "/findCourseStudyPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "科目编号ID", name = "courseId", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findCourseStudyPage(@RequestBody CourseStudyFindPage req, HttpServletRequest request) {
        valideSort(req.getPage(), req.getSize());
        MyAssert.isNull(req.getCourseId(), DefineCode.ERR0010, "课程Id不是空");
        return WebResult.okResult(courseService.findCourseStudyPageAll(req.getCourseId(), req.getStudentId(),
                PageRequest.of(req.getPage(), req.getSize())));
    }

    @UserLoginToken
    @ApiOperation(value = "删除课程轮播图")
    @ApiImplicitParam(name = "courseId", value = "科目/课程id", dataType = "string", required = true, paramType = "form")
    @PostMapping(path = "/deleteImagesByCourseId")
    public WebResult deleteImagesByCourseId(@RequestBody String courseId) {
        MyAssert.isNull(courseId, DefineCode.ERR0010, "课程id不为空");
        return WebResult.okResult(courseService.deleteImagesByCourseId(String.valueOf(JSONObject.parseObject(courseId).getString("courseId"))));
    }


    @UserLoginToken
    @ApiOperation(value = "学生端登录后加载对应的课程信息")
    @GetMapping("/studentCourseList")
    public WebResult findCourseStudent(HttpServletRequest request) {
        String token = request.getHeader("token");
        // 微信端获取用户学生Id,教师
        String studentId = tokenService.getStudentId(token);
        if (tokenService.isStudent(token)) {
            String classId = tokenService.getClassId(token);
            List<CourseVo> vos = courseService.findCourseVoByClassId(classId);
            if (vos != null) {
                return WebResult.okResult(vos);
            }
            List<CourseTeacherVo> courseIds = teachPlanCourseService.findCourseIdAndTeacherIdByClassId(classId)
                    .stream().filter(Objects::nonNull)
                    .map(vo -> new CourseTeacherVo(vo.getCourseId(), vo.getTeacherId(), vo.getStatus(), vo.getCountStatus())).collect(toList());
            return WebResult.okResult(courseService.findByCourseNumberAndTeacherId(courseIds, classId, studentId));
        } else {
            //不是学生是教师只能查看自己创建的课程信息
            return WebResult.okResult(courseService.findAllCourseVoByCreateUser(studentId));
        }
    }

    @ApiOperation(value = "物理全部删除课程信息和对应的计划课程")
    @DeleteMapping(path = "/{courseId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "科目/课程id", dataType = "string", required = true, paramType = "form")
    })
    public WebResult deleteById(@PathVariable String courseId, HttpServletRequest httpServletRequest) {
        MyAssert.isTrue(StrUtil.isBlank(courseId), DefineCode.ERR0010, "课程id不能为空");
        Course course = courseService.getById(courseId);
        MyAssert.isTrue(teachPlanCourseService.isAfterOrEqualsCourseNumberAndTeacherId(course.getCourseNumber(), course.getCreateUser()), DefineCode.ERR0010, "正在计划中的课程信息不能删除");
        //删除计划上的课程
        teachPlanCourseService.deleteTeachCourseByCourseNumberAndTeacherId(course.getCourseNumber(), course.getCreateUser());
        //删除对应的课程资料
        coursewareService.deleteByCourseId(courseId);
        //删除对应的章节资料
        courseChapterService.deleteAllByCourseId(courseId);
        //删除对应的学生学习记录
        courseVerifyVoService.deleteAllByCourseId(courseId);
        //删除课程信息
        courseService.deleteById(courseId);
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String userName = tokenService.getUserName(token);
        String centerId = tokenService.getCenterAreaId(token);
        String centerName = learnCenterService.findByCenterId(centerId).getCenterName();
        log.info("delete course courseId : [{}], userId : [{}]", courseId, userId);
        Map<String, String> map = new HashMap<>(6);
        map.put("courseId", courseId);
        map.put("courseNumber", course.getCourseNumber());
        map.put("courseName", course.getCourseName());
        userRecordService.save(new UserRecord(userId, userName, centerId, centerName, "删除课程信息", "删除", map));
        return WebResult.okResult();
    }
}