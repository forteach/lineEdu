package com.project.portal.teachplan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.common.keyword.Dic;
import com.project.base.exception.MyAssert;
import com.project.mongodb.domain.UserRecord;
import com.project.mongodb.service.UserRecordService;
import com.project.portal.response.WebResult;
import com.project.portal.teachplan.request.TeachPlanCourseFindAllPageRequest;
import com.project.portal.teachplan.request.TeachPlanPageAllRequest;
import com.project.portal.teachplan.request.TeachPlanSaveUpdateRequest;
import com.project.portal.teachplan.request.TeachPlanVerifyRequest;
import com.project.schoolroll.domain.online.StudentOnLine;
import com.project.schoolroll.repository.dto.StudentOnLineDto;
import com.project.schoolroll.service.LearnCenterService;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.service.TeachPlanCourseService;
import com.project.teachplan.service.TeachService;
import com.project.teachplan.vo.TeachPlanVo;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.base.common.keyword.Dic.*;
import static com.project.portal.request.ValideSortVo.valideSort;
@Slf4j
@RestController
@RequestMapping(path = "/teachPlan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "在线计划管理", tags = {"在线计划管理"})
public class TeachPlanController {

    private final TeachService teachService;
    private final TokenService tokenService;
    private final StudentOnLineService studentOnLineService;
    private final UserRecordService userRecordService;
    private final LearnCenterService learnCenterService;

    private final TeachPlanCourseService teachPlanCourseService;

    @Autowired
    public TeachPlanController(TeachService teachService, TeachPlanCourseService teachPlanCourseService, StudentOnLineService studentOnLineService,
                               TokenService tokenService, UserRecordService userRecordService, LearnCenterService learnCenterService) {
        this.teachService = teachService;
        this.teachPlanCourseService = teachPlanCourseService;
        this.tokenService = tokenService;
        this.studentOnLineService = studentOnLineService;
        this.userRecordService = userRecordService;
        this.learnCenterService = learnCenterService;
    }

    @UserLoginToken
    @ApiOperation(value = "保存修改教学计划")
    @PostMapping(path = "/saveUpdate")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "planName", dataType = "string", value = "计划名称", paramType = "form"),
//            @ApiImplicitParam(name = "grade", value = "年级", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "className", value = "班级名称", dataType = "string", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "specialtyName", value = "专业名称", dataType = "string", paramType = "form")
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", paramType = "form"),
            @ApiImplicitParam(name = "planAdmin", dataType = "string", value = "计划负责人", paramType = "form"),
            @ApiImplicitParam(name = "startDate", dataType = "string", value = "计划结束时间", paramType = "form"),
            @ApiImplicitParam(name = "endDate", dataType = "string", value = "计划结束时间", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "班级id", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "courses", dataType = "list", value = "课程id集合", paramType = "form"),
            @ApiImplicitParam(name = "courseId", dataType = "string", value = "课程id", paramType = "form"),
            @ApiImplicitParam(name = "credit", dataType = "string", value = "学分", paramType = "form"),
            @ApiImplicitParam(name = "onLinePercentage", dataType = "int", value = "线上占比", paramType = "form"),
            @ApiImplicitParam(name = "linePercentage", dataType = "int", value = "线下占比", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注说明", dataType = "string", paramType = "form")
    })
    public WebResult saveUpdate(@RequestBody TeachPlanSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        String centerAreaId = tokenService.getCenterAreaId(token);
        TeachPlanVerify teachPlan = new TeachPlanVerify();
        BeanUtil.copyProperties(request, teachPlan);
        teachPlan.setCenterAreaId(centerAreaId);
        teachPlan.setCreateUser(userId);
        teachPlan.setUpdateUser(userId);
        return WebResult.okResult(teachService.saveUpdatePlan(teachPlan, request.getCourses(), request.getRemark()));
    }

//    @UserLoginToken
//    @ApiOperation(value = "保存修改计划对应的班级接口")
//    @PostMapping(path = "/saveUpdateClass")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "classIds", dataType = "list", value = "班级id集合", paramType = "form"),
//            @ApiImplicitParam(name = "remark", value = "备注说明", dataType = "string", paramType = "form")
//    })
//    public WebResult saveUpdateClass(@RequestBody TeachPlanClassSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
//        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
//        MyAssert.isTrue(request.getClassIds().isEmpty(), DefineCode.ERR0010, "班级信息不为空");
//        String token = httpServletRequest.getHeader("token");
//        String centerAreaId = tokenService.getCenterAreaId(token);
//        String userId = tokenService.getUserId(token);
//        return WebResult.okResult(teachService.saveUpdatePlanClass(request.getPlanId(), request.getClassIds(), request.getRemark(), centerAreaId, userId));
//    }

//    @UserLoginToken
//    @ApiOperation(value = "保存修改计划对应的课程接口")
//    @PostMapping(path = "/saveUpdateCourse")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "string", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "teacherId", dataType = "string", value = "创建教师id", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "courses", dataType = "list", value = "课程id集合", paramType = "form"),
//            @ApiImplicitParam(name = "courseId", dataType = "string", value = "课程id", paramType = "form"),
//            @ApiImplicitParam(name = "credit", dataType = "string", value = "学分", paramType = "form"),
//            @ApiImplicitParam(name = "onLinePercentage", dataType = "int", value = "线上占比", paramType = "form"),
//            @ApiImplicitParam(name = "linePercentage", dataType = "int", value = "线下占比", paramType = "form"),
//            @ApiImplicitParam(name = "remark", value = "备注说明", dataType = "string", paramType = "form")
//    })
//    public WebResult saveUpdateCourse(@RequestBody TeachPlanCourseSaveUpdateRequest request, HttpServletRequest httpServletRequest) {
//        MyAssert.isNull(request.getPlanId(), DefineCode.ERR0010, "计划id不为空");
//        MyAssert.isTrue(request.getCourses().isEmpty(), DefineCode.ERR0010, "课程信息不为空");
//        String token = httpServletRequest.getHeader("token");
//        String centerAreaId = tokenService.getCenterAreaId(token);
//        String userId = tokenService.getUserId(token);
//        return WebResult.okResult(teachService.saveUpdatePlanCourse(request.getPlanId(), request.getCourses(), request.getRemark(), centerAreaId, userId));
//    }

    @UserLoginToken
    @ApiOperation(value = "分页查询教学计划")
    @PostMapping(path = "/findByPlanIdPageAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "verifyStatus", value = "修改状态", dataType = "0 (同意) 1 (已经提交) 2 (不同意)", paramType = "form"),
            @ApiImplicitParam(name = "specialtyName", dataType = "string", value = "专业名称", paramType = "form"),
            @ApiImplicitParam(name = "grade", value = "年级", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "className", value = "班级", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "centerName", value = "学习中心名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", required = true, paramType = "query")
    })
    public WebResult findByPlanIdPageAll(@RequestBody TeachPlanPageAllRequest request, HttpServletRequest httpServletRequest) {
        valideSort(request.getPage(), request.getSize());
        String token = httpServletRequest.getHeader("token");
        PageRequest of = PageRequest.of(request.getPage(), request.getSize());
        TeachPlanVo vo = new TeachPlanVo();
        BeanUtil.copyProperties(request, vo);
        if (!tokenService.isAdmin(token)) {
            vo.setCenterAreaId(tokenService.getCenterAreaId(token));
        }
        return WebResult.okResult(teachService.findAllPage(vo, of));
//        if (tokenService.isAdmin(token)) {
//            return WebResult.okResult(teachService.findAllPageByPlanIdAndVerifyStatus(request.getPlanId(), request.getVerifyStatus(), pageRequest));
//        } else {
//            String centerAreaId = tokenService.getCenterAreaId(token);
//            if (StrUtil.isNotBlank(request.getPlanId())) {
//                return WebResult.okResult(teachService.findAllPageDtoByCenterAreaIdAndPlanId(centerAreaId, request.getPlanId(), request.getVerifyStatus(), pageRequest));
//            } else {
//                return WebResult.okResult(teachService.findAllPageDtoByCenterAreaId(centerAreaId, request.getVerifyStatus(), pageRequest));
//            }
//        }
    }

    @UserLoginToken
    @ApiOperation(value = "移除(逻辑)对应计划的信息")
    @PostMapping(path = "/removeByPlanId")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form")
    public WebResult removeByPlanId(@RequestBody String planId) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        teachService.removeByPlanId(JSONObject.parseObject(planId).getString("planId"));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "删除(物理)对应计划的信息")
    @DeleteMapping(path = "/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form")
    public WebResult deleteByPlanId(@PathVariable String planId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);
        log.info("delete plan planId : [{}], userId : [{}]", planId, userId);
        teachService.deleteByPlanId(planId);
        String userName = tokenService.getUserName(token);
        String centerId = tokenService.getCenterAreaId(token);
        String centerName = learnCenterService.findByCenterId(centerId).getCenterName();
        Map<String,String> map = new HashMap<>(2);
        map.put("planId", planId);
        userRecordService.save(new UserRecord(userId, userName, centerId, centerName, "删除计划信息", "删除", map));
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "查询计划对应课程信息")
    @PostMapping(path = "/course/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "query")
    public WebResult findAllCourseByPlanId(@PathVariable String planId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        if (tokenService.isStudent(httpServletRequest.getHeader("token"))) {
            return WebResult.okResult(teachPlanCourseService.findAllCourseByPlanId(planId));
        }else {
            return WebResult.okResult(teachPlanCourseService.findAllCourseVerifyByPlanId(planId));
        }
    }

//    @UserLoginToken
//    @ApiOperation(value = "查询计划对应班级信息")
//    @PostMapping(path = "/class/{planId}")
//    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "query")
//    public WebResult findAllClassByPlanId(@PathVariable String planId, HttpServletRequest request) {
//        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
//        String token = request.getHeader("token");
//        if (tokenService.isStudent(token)) {
//            return WebResult.okResult(teachService.findAllClassByPlanId(planId));
//        }else {
//            return WebResult.okResult(teachService.findAllClassVerifyByPlanId(planId));
//        }
//    }

    @UserLoginToken
    @ApiOperation(value = "更新教学计划状态")
    @PutMapping(path = "/status/{planId}")
    @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form")
    public WebResult updateStatus(@PathVariable String planId, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(planId, DefineCode.ERR0010, "计划id不为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        teachService.updateStatus(planId, userId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation(value = "审核教学计划")
    @PostMapping(path = "/verifyTeachPlan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", dataType = "string", value = "计划id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注信息", dataType = "string", paramType = "form")
    })
    public WebResult verifyTeachPlan(@RequestBody TeachPlanVerifyRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isTrue(StrUtil.isBlank(request.getPlanId()), DefineCode.ERR0010, "计划id不为空");
        MyAssert.isNull(request.getVerifyStatus(), DefineCode.ERR0010, "计划状态不能为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        teachService.verifyTeachPlan(request.getPlanId(), request.getVerifyStatus(), request.getRemark(), userId);
        return WebResult.okResult();
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查询全部有效的教学计划")
    public WebResult findAllPlan(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        if (tokenService.isAdmin(token)){
            return WebResult.okResult(teachService.findAllPlan());
        }
        String centerId = tokenService.getCenterAreaId(token);
        return WebResult.okResult(teachService.findAllPlanByCenterId(centerId));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询计划对应的课程学生信息")
    @PostMapping(path = "/findPlanCourseAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyName", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllStudyCourse(@RequestBody TeachPlanCourseFindAllPageRequest req, HttpServletRequest httpServletRequest){
//        MyAssert.isFalse(checkTeachSearch(req), DefineCode.ERR0010, "专业和年级必填");
        checkTeachSearch(req);
        PageRequest request = PageRequest.of(req.getPage(), req.getSize());
        String token = httpServletRequest.getHeader("token");
        if (tokenService.isAdmin(token)) {
            return WebResult.okResult(teachService.findAllPageDtoByPlanId(req.getStudentName(), req.getClassName(), req.getGrade(), req.getSpecialtyName(), "", request));
        }
        String centerId = tokenService.getCenterAreaId(token);
        return WebResult.okResult(teachService.findAllPageDtoByPlanId(req.getStudentName(), req.getClassName(), req.getGrade(), req.getSpecialtyName(), centerId, request));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询计划课程的在线学生学习成绩信息")
    @PostMapping(path = "/findPlanCourseScoreAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyName", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllStudyCourseScore(@RequestBody TeachPlanCourseFindAllPageRequest req, HttpServletRequest httpServletRequest){
//        MyAssert.isFalse(checkTeachSearch(req), DefineCode.ERR0010, "专业和年级必填");
        checkTeachSearch(req);
        PageRequest request = PageRequest.of(req.getPage(), req.getSize());
        String token = httpServletRequest.getHeader("token");
        if(tokenService.isAdmin(token)){
            return WebResult.okResult(teachService.findScoreAllPageDtoByPlanId(req.getStudentName(), req.getClassName(), req.getGrade(), req.getSpecialtyName(), "", request));
        }
        String centerId = tokenService.getCenterAreaId(token);
        return WebResult.okResult(teachService.findScoreAllPageDtoByPlanId(req.getStudentName(), req.getClassName(), req.getGrade(), req.getSpecialtyName(), centerId, request));
    }

    @UserLoginToken
    @ApiOperation(value = "分页查询计划课程的在线学生学习成绩信息")
    @PostMapping(path = "/importPlanCourseScoreAllPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "学生名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialtyName", value = "学生名称", dataType = "string", paramType = "query")
    })
    public WebResult importAllStudyCourseScore(@RequestBody TeachPlanCourseFindAllPageRequest req, HttpServletRequest httpServletRequest){
        MyAssert.isFalse(checkTeach(req), DefineCode.ERR0010, "专业和年级必填");
        String token = httpServletRequest.getHeader("token");
        String centerId = tokenService.getCenterAreaId(token);
        PageRequest of = PageRequest.of(0, 10000);
        List<StudentOnLineDto> list;
        if(tokenService.isAdmin(token)){
            list = studentOnLineService.findStudentOnLineDto(of, req.getStudentName(), TAKE_EFFECT_OPEN, "", req.getGrade(), req.getSpecialtyName(), req.getClassName()).getContent();
        }else {
            list = studentOnLineService.findStudentOnLineDto(of, req.getStudentName(), TAKE_EFFECT_OPEN, centerId, req.getGrade(), req.getSpecialtyName(), req.getClassName()).getContent();
        }
        if (!list.isEmpty()){

        }
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0010, "不存在要导出的数据");
        return WebResult.okResult();
    }

    private boolean checkTeachSearch(TeachPlanCourseFindAllPageRequest req){
        valideSort(req.getPage(), req.getSize());
        return checkTeach(req);
    }

    private boolean checkTeach(TeachPlanCourseFindAllPageRequest req){
        if (StrUtil.isNotBlank(req.getClassName())){
            return true;
        }
        if (StrUtil.isNotBlank(req.getStudentName())){
            return true;
        }
        //必须是年级和专业唯一才能确定对应的计划
        if (StrUtil.isNotBlank(req.getGrade()) && StrUtil.isNotBlank(req.getSpecialtyName())){
            return true;
        }
        return false;
    }
//
//    @UserLoginToken
//    @ApiOperation(value = "分页查询计划对应的课程学生信息")
//    @PostMapping(path = "/findPlanCourseAllPage")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "planId", value = "计划Id", dataType = "string", required = true, paramType = "query"),
//            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
//            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
//    })
//    public WebResult findAllStudyCourse(@RequestBody TeachPlanCourseFindAllPageRequest req){
//        valideSort(req.getPage(), req.getSize());
//        MyAssert.isNull(req.getPlanId(), DefineCode.ERR0010, "计划Id不能为空");
//        String key = PLAN_COURSE_STUDENT_STUDY.concat(req.getPlanId()).concat("&").concat(String.valueOf(req.getPage())).concat("&").concat(String.valueOf(req.getSize()));
//        return WebResult.okResult(teachService.findAllPageDtoByPlanId(req.getPlanId(), key, PageRequest.of(req.getPage(), req.getSize())));
//    }
//
//    @UserLoginToken
//    @ApiOperation(value = "分页查询计划课程的在线学生学习成绩信息")
//    @PostMapping(path = "/findPlanCourseScoreAllPage")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "planId", value = "计划Id", dataType = "string", required = true, paramType = "query"),
//            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
//            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
//    })
//    public WebResult findAllStudyCourseScore(@RequestBody TeachPlanCourseFindAllPageRequest req){
//        valideSort(req.getPage(), req.getSize());
//        MyAssert.isNull(req.getPlanId(), DefineCode.ERR0010, "计划Id不能为空");
//        String key = PLAN_COURSE_STUDENT_SCORE.concat(req.getPlanId()).concat("&").concat(String.valueOf(req.getPage())).concat("&").concat(String.valueOf(req.getSize()));
//        return WebResult.okResult(teachService.findScoreAllPageDtoByPlanId(req.getPlanId(), key, PageRequest.of(req.getPage(), req.getSize())));
//    }
}