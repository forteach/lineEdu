package com.project.portal.databank.controller;

import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseService;
import com.project.course.service.CoursewareService;
import com.project.databank.domain.verify.CourseVerifyVo;
import com.project.databank.service.ChapteDataService;
import com.project.databank.service.CourseVerifyVoService;
import com.project.portal.databank.request.CourseVerifyRequest;
import com.project.portal.databank.request.FindDatumVerifyRequest;
import com.project.portal.response.WebResult;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_AGREE;
import static com.project.databank.domain.verify.CourseVerifyEnum.*;
import static com.project.portal.request.ValideSortVo.valideSort;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:49
 * @version: 1.0
 * @description:
 */
@RestController
@RequestMapping(path = "/courseVerify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "课程章节信息审核", tags = {"分页查询课程章节信息、审核"})
public class CourseVerifyController {
    @Resource
    private TokenService tokenService;
    @Resource
    private CourseVerifyVoService courseVerifyVoService;
    @Resource
    private ChapteDataService chapteDataService;
    @Resource
    private CoursewareService coursewareService;
    @Resource
    private CourseService courseService;

    @ApiOperation(value = "查询需要审核的课程信息")
    @UserLoginToken
    @PostMapping("/findAllPageVerify")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "courseName", value = "课程名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllPageVerify(@RequestBody FindDatumVerifyRequest request) {
        valideSort(request.getPage(), request.getSize());
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        if (StrUtil.isNotBlank(request.getCourseId())) {
            return WebResult.okResult(courseVerifyVoService.findAllPage(request.getCourseId(), pageRequest));
        }
        return WebResult.okResult(courseVerifyVoService.findAllPage(pageRequest));
    }

    @ApiOperation(value = "课程计划审核")
    @UserLoginToken
    @PostMapping(path = "/verify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要审核的id", dataType = "string", required = true),
            @ApiImplicitParam(name = "verifyStatus", value = "修改状态", dataType = "0 (同意) 1 (已经提交) 2 (不同意)", required = true, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "form")
    })
    public WebResult verifyStatus(@RequestBody CourseVerifyRequest request, HttpServletRequest httpServletRequest) {
        MyAssert.isNull(request.getId(), DefineCode.ERR0010, "审核id不能为空");
        MyAssert.isNull(request.getVerifyStatus(), DefineCode.ERR0010, "审核状态不能为空");
        String token = httpServletRequest.getHeader("token");
        String userId = tokenService.getUserId(token);

        Optional<CourseVerifyVo> optionalCourseVerifyVo = courseVerifyVoService.findById(request.getId());
        MyAssert.isFalse(optionalCourseVerifyVo.isPresent(), DefineCode.ERR0014, "不存在要修改的审核信息");
        CourseVerifyVo verifyVo = optionalCourseVerifyVo.get();
        //是文件资料信息
        String type = verifyVo.getCourseType();

        if (CHAPTER_DATE.getValue().equals(type)) {
            //是章节资料
            chapteDataService.verifyData(new com.project.databank.web.vo.CourseVerifyRequest(verifyVo.getFileId(),
                    request.getVerifyStatus(), request.getRemark(), userId), verifyVo.getDatumType());
        }
        if (COURSE_CHAPTER_QUESTION.getValue().equals(type) && VERIFY_STATUS_AGREE.equals(request.getVerifyStatus())) {
            // 是习题需要审核
            courseVerifyVoService.verifyQuestion(new com.project.databank.web.vo.CourseVerifyRequest(verifyVo.getQuestionId(),
                    request.getVerifyStatus(), request.getRemark(), userId));
        }
        if (COURSE_FILE_DATA.getValue().equals(type)) {
            //课程的课件
            coursewareService.updateVerifyCourseware(new com.project.databank.web.vo.CourseVerifyRequest(verifyVo.getFileId(),
                    request.getVerifyStatus(), request.getRemark(), userId));
            int videoTimeSum = coursewareService.findVideoTimeSum(verifyVo.getCourseId());
            courseService.updateCourseTime(verifyVo.getCourseId(), videoTimeSum);
        }
        //修改数据
        verifyVo.setUpdateUser(userId);
        verifyVo.setVerifyStatus(request.getVerifyStatus());
        verifyVo.setRemark(request.getRemark());
        courseVerifyVoService.save(verifyVo);
        return WebResult.okResult();
    }

//    @UserLoginToken
//    @ApiOperation(value = "查询需要修改的课程信息")
//    @GetMapping(path = "/findCourse")
//    public WebResult findAllCourse() {
//        return WebResult.okResult(courseVerifyVoService.findVerifyCourse());
//    }
}