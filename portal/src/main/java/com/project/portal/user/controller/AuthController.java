package com.project.portal.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.request.SortVo;
import com.project.portal.request.ValideSortVo;
import com.project.portal.response.WebResult;
import com.project.portal.user.request.UpdatePassWordRequest;
import com.project.portal.user.request.UserLoginRequest;
import com.project.token.annotation.PassToken;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.user.service.RoleService;
import com.project.user.service.UserService;
import com.project.user.web.req.UpdatePassWordReq;
import com.project.user.web.req.UserLoginReq;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-2-22 13:45
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@Api(value = "用户认证", tags = {"用户操作"})
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {

    private final UserService userService;

    private final RoleService roleService;

    private final TokenService tokenService;

    public AuthController(UserService userService, RoleService roleService, TokenService tokenService){
        this.userService = userService;
        this.roleService = roleService;
        this.tokenService = tokenService;
    }

    @PassToken
    @ApiOperation("用户登录")
    @PostMapping(path = "/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherCode", value = "学习中心名称/教师手机号码/管理员名称", required = true, dataType = "string", paramType = "from"),
            @ApiImplicitParam(name = "passWord", value = "密码", required = true, dataType = "string", paramType = "from")
    })
    public WebResult login(@RequestBody UserLoginRequest req, HttpServletRequest httpServletRequest){
        MyAssert.isTrue(StrUtil.isBlank(req.getTeacherCode()), DefineCode.ERR0010, "用户名称不为空");
        MyAssert.isTrue(StrUtil.isBlank(req.getPassWord()), DefineCode.ERR0010, "密码不为空");
        UserLoginReq userLoginReq = new UserLoginReq();
        BeanUtil.copyProperties(req, userLoginReq);
        userLoginReq.setIp(httpServletRequest.getRemoteHost());
        return WebResult.okResult(userService.login(userLoginReq));
    }

    @ApiOperation("重置管理端账户密码为初始化密码")
    @PostMapping("/resetPassWord")
    @ApiImplicitParam(name = "id",value = "教师代码", required = true, dataType = "string",paramType = "from")
    @UserLoginToken
    public WebResult resetPassWord(@RequestBody String id, HttpServletRequest httpServletRequest){
        MyAssert.blank(id, DefineCode.ERR0010, "id不为空");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        return WebResult.okResult(userService.resetPassWord(JSONObject.parseObject(id).getString("id"), userId));
    }

    @UserLoginToken
    @ApiOperation("修改密码")
    @PostMapping("/updatePassWord")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "旧密码", name = "oldPassWord", required = true, dataType = "string", paramType = "from"),
            @ApiImplicitParam(value = "新密码", name = "newPassWord", required = true, dataType = "string", paramType = "from")
    })
    @ApiResponse(code = 0, message = "OK")
    public WebResult updatePassWord(@RequestBody UpdatePassWordRequest passWordRequest, HttpServletRequest request){
        MyAssert.isTrue(StrUtil.isBlank(passWordRequest.getOldPassWord()), DefineCode.ERR0010, "旧密码不能为空");
        MyAssert.isTrue(StrUtil.isBlank(passWordRequest.getNewPassWord()), DefineCode.ERR0010, "新密码不能为空");
        MyAssert.isTrue(passWordRequest.getOldPassWord().equals(passWordRequest.getNewPassWord()), DefineCode.ERR0010, "新密码和原密码不能相同");
        UpdatePassWordReq updatePassWordReq = new UpdatePassWordReq();
        BeanUtil.copyProperties(passWordRequest, updatePassWordReq);
        updatePassWordReq.setTeacherCode(tokenService.getUserId(request.getHeader("token")));
        userService.updatePassWord(updatePassWordReq);
        return WebResult.okResult();
    }

    @UserLoginToken
    @ApiOperation("修改教师用户状态")
    @PutMapping("/updateState")
    @ApiImplicitParam(name = "teacherCode", value = "教师代码", required = true, dataType = "string", paramType = "from")
    @ApiResponse(code = 0, message = "OK")
    public WebResult updateState(@RequestBody String teacherCode, HttpServletRequest httpServletRequest){
        MyAssert.blank(teacherCode, DefineCode.ERR0010, "教师代码不为空");
        String teacherCodeStr = JSONObject.parseObject(teacherCode).getString("teacherCode");
        String userId = tokenService.getUserId(httpServletRequest.getHeader("token"));
        userService.updateState(teacherCodeStr, userId);
        return WebResult.okResult();
    }

    /**
     * 用户列表
     *
     * @return
     */
    @UserLoginToken
    @PostMapping(value = "/users")
    @ApiOperation(value = "用户列表", notes = "通过 分页 及排序获得用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页从0开始", required = true, dataType = "int", type = "int", example = "0"),
            @ApiImplicitParam(name = "size", value = "每页数量", required = true, dataType = "int", type = "int", example = "10")
    })
    public WebResult userList(@Valid @RequestBody @ApiParam(value = "分页对象", required = true) SortVo sortVo) {
        ValideSortVo.valideSort(sortVo.getPage(), sortVo.getSize());
        return WebResult.okResult(roleService.findUsersInfo(sortVo.getPage(), sortVo.getSize()));
    }
}