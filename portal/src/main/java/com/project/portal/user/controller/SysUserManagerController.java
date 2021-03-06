package com.project.portal.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.user.request.SysUserEditRequest;
import com.project.token.annotation.UserLoginToken;
import com.project.user.service.UserMgrService;
import com.project.user.web.req.SysUserEditReq;
import com.project.user.web.vo.CastVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/1 2:38
 */
@Slf4j
@RestController
@RequestMapping(path = "/sysUserMgr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "/sysUserMgr", tags = {"用户角色相关操作 "})
public class SysUserManagerController {

    private final UserMgrService userMgrService;

    private SysUserManagerController(UserMgrService userMgrService) {
        this.userMgrService = userMgrService;
    }


    /**
     * @Description: 分配角色
     * @Param: castVo
     * @return:
     */
    @PostMapping(value = "/cast")
    @ApiOperation(value = "分配角色", notes = "分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "string", paramType = "from"),
            @ApiImplicitParam(name = "userIds", value = "用户集合", required = true, dataType = "string", paramType = "from", example = "[\"1\",\"2\",\"3\"]")
    })
    @ApiResponse(code = 0, message = "OK")
    @UserLoginToken
    public WebResult cast(@RequestBody @ApiParam(value = "分配角色", required = true) CastVo castVo) {
        MyAssert.blank(castVo.getRoleId(), DefineCode.ERR0010, "角色id不为空");
        MyAssert.egt(0, castVo.getUserIds().size(), DefineCode.ERR0010, "用户集合id不为空");
        userMgrService.updateUserRole(castVo.getRoleId(), castVo.getUserIds());
        return WebResult.okResult();
    }

    /**
     * @Description: 编辑用户
     * @Param: user
     * @return:
     */
    @UserLoginToken
    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑用户", notes = "编辑/保存用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户主键", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "registerPhone", value = "注册手机号码", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", paramType = "form")
    })
    public WebResult edit(@Valid @RequestBody @ApiParam(value = "编辑/保存用户", required = true) SysUserEditRequest user) {
        MyAssert.isNull(user.getId(), DefineCode.ERR0010, "用户id不能为空");
        if (StrUtil.isNotBlank(user.getRegisterPhone())) {
            MyAssert.isFalse(Validator.isMobile(user.getRegisterPhone()), DefineCode.ERR0010, "手机号码格式不正确");
        }

        SysUserEditReq sysUserEditReq = new SysUserEditReq();
        BeanUtil.copyProperties(user, sysUserEditReq);
        return WebResult.okResult(userMgrService.edit(sysUserEditReq));
    }
}