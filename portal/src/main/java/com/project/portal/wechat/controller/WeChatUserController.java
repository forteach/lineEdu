package com.project.portal.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdcardUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.service.CourseService;
import com.project.portal.request.SortVo;
import com.project.portal.request.ValideSortVo;
import com.project.portal.response.WebResult;
import com.project.portal.wechat.req.BindingUserReq;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.teachplan.service.TeachPlanCourseService;
import com.project.token.annotation.PassToken;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.user.domain.SysUsers;
import com.project.user.service.UserService;
import com.project.wechat.mini.app.config.WeChatMiniAppConfig;
import com.project.wechat.mini.app.service.WeChatUserService;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 19-1-10 11:19
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Api(value = "微信用户操作信息", description = "用户操作相关接口", tags = {"微信用户操作"})
@RestController
@RequestMapping("/weChat")
public class WeChatUserController {

    private TokenService tokenService;
    private final WeChatUserService weChatUserService;
    private final StudentOnLineService studentOnLineService;
    private final CourseService courseService;
    private final TeachPlanCourseService teachPlanCourseService;
    private final UserService userService;

    @Autowired
    public WeChatUserController(WeChatUserService weChatUserService, StudentOnLineService studentOnLineService, UserService userService,
                                TeachPlanCourseService teachPlanCourseService, CourseService courseService, TokenService tokenService) {
        this.weChatUserService = weChatUserService;
        this.tokenService = tokenService;
        this.studentOnLineService = studentOnLineService;
        this.courseService = courseService;
        this.teachPlanCourseService = teachPlanCourseService;
        this.userService = userService;
    }

    @UserLoginToken
    @ApiOperation(value = "绑定微信用户登录信息")
    @PostMapping("/binding")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "姓名", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "stuIDCard", value = "身份证号码/电话号码", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "学生类型 1 校内学生，2 在线学生", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "signature", value = "sha1( rawData + session_key )", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "rawData", value = "rawData", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "encryptedData", value = "加密数据", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "iv", value = "数据接口返回", dataType = "string", paramType = "form"),
    })
    public WebResult binding(@RequestBody BindingUserReq bindingUserReq, HttpServletRequest request) {
        MyAssert.blank(bindingUserReq.getStuIDCard(), DefineCode.ERR0010, "身份证号码/手机号码不为空");
        MyAssert.blank(bindingUserReq.getStudentName(), DefineCode.ERR0010, "用户名不为空");
        BindingUserRequest bindingUser = new BindingUserRequest();
        BeanUtil.copyProperties(bindingUserReq, bindingUser);
        bindingUser.setOpenId(tokenService.getOpenId(request.getHeader("token")));
        if (IdcardUtil.isValidCard(bindingUserReq.getStuIDCard())) {
            //是学生
//            MyAssert.blank(bindingUserReq.getType(), DefineCode.ERR0010, "学生类型不为空");
            return WebResult.okResult(weChatUserService.bindingUser(bindingUser));
        } else {
            //是教师或者学习中心
            SysUsers users = userService.checkUserNameAndPassWord(bindingUser.getStudentName(), bindingUser.getStuIDCard());
            String userName = users.getUserName();
            String teacherId = users.getTeacherId();
            String centerId = users.getCenterAreaId();
            String roleCode = users.getRoleCode();
            return WebResult.okResult(weChatUserService.bindTeacher(bindingUser, teacherId, userName, centerId, roleCode));
        }
    }

    @PassToken
    @ApiOperation(value = "微信小程序登录接口")
    @GetMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信登录凭证(code)", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "portrait", value = "用户头像url", dataType = "string", paramType = "form")
    })
    public WebResult login(@ApiParam(name = "code", value = "微信登录code") String code, @ApiParam(value = "portrait") String portrait, HttpServletRequest httpServletRequest) {
        MyAssert.blank(code, DefineCode.ERR0010, "code is null");
        final WxMaService wxService = WeChatMiniAppConfig.getMaService();
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            //可以增加自己的逻辑，关联业务相关数据
            String ip = httpServletRequest.getRemoteHost();
            return WebResult.okResult(weChatUserService.bindingToken(session, portrait, ip));
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return WebResult.failResult(DefineCode.ERR0009, e.getMessage());
        }
    }

    @ApiOperation(value = "解绑微信登录用户")
    @PostMapping(path = "/untying")
    public WebResult untying(HttpServletRequest httpServletRequest) {
        String openId = tokenService.getOpenId(httpServletRequest.getHeader("token"));
        weChatUserService.untying(openId);
        return WebResult.okResult();
    }

    @ApiOperation(value = "微信端查询我的学习情况")
    @PostMapping(path = "/learningSituation")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult learningSituation(@RequestBody SortVo sortVo, HttpServletRequest httpServletRequest) {
        ValideSortVo.valideSort(sortVo.getPage(), sortVo.getSize());
        String token = httpServletRequest.getHeader("token");
        String studentId = tokenService.getStudentId(token);
        PageRequest of = PageRequest.of(sortVo.getPage(), sortVo.getSize());
        return WebResult.okResult(courseService.findAllByStudentId(studentId, of));
    }

    @ApiOperation(value = "微信端查询所有的课程")
    @PostMapping(path = "/findAllCourse")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分页", dataType = "int", name = "page", example = "0", required = true, paramType = "query"),
            @ApiImplicitParam(value = "每页数量", dataType = "int", name = "size", example = "15", required = true, paramType = "query")
    })
    public WebResult findAllCourse(@RequestBody SortVo sortVo, HttpServletRequest httpServletRequest) {
        ValideSortVo.valideSort(sortVo.getPage(), sortVo.getSize());
        String token = httpServletRequest.getHeader("token");
        String classId = tokenService.getClassId(token);
        PageRequest of = PageRequest.of(sortVo.getPage(), sortVo.getSize());
        List<String> courseIds = teachPlanCourseService.findAllByClassId(classId, of);
        return WebResult.okResult(courseService.findAllByCourseNumberIds(courseIds));
    }

    @UserLoginToken
    @ApiOperation(value = "微信端查询学生自己个人详细信息")
    @GetMapping(path = "/myInfo")
    public WebResult myInfo(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String studentId = tokenService.getStudentId(token);
        return WebResult.okResult(studentOnLineService.findStudentById(studentId));
    }

    @UserLoginToken
    @ApiOperation(value = "微信端查询学生自己学习总情况")
    @GetMapping(path = "/myCourseAllStudy")
    public WebResult myCourseAllStudy(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String studentId = tokenService.getStudentId(token);
        String classId = tokenService.getClassId(token);
        List<String> courseIds = teachPlanCourseService.findAllByClassId(classId);
        //查询线下课程Id集合 课程类型　1.线上，2.线下,3.混合
        List<String> offlineIds = teachPlanCourseService.findAllByClassIdAndType(classId, "2");
        return WebResult.okResult(courseService.findCourseAllStudyByStudentId(studentId, courseIds, offlineIds));
    }
}
