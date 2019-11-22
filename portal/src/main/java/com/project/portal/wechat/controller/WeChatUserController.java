package com.project.portal.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdcardUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.wechat.req.BindingUserReq;
import com.project.token.annotation.PassToken;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.wechat.mini.app.config.WeChatMiniAppConfig;
import com.project.wechat.mini.app.service.WeChatUserService;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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

    @Autowired
    public WeChatUserController(WeChatUserService weChatUserService, TokenService tokenService) {
        this.weChatUserService = weChatUserService;
        this.tokenService = tokenService;
    }

    @PassToken
    @ApiOperation(value = "微信小程序登录接口")
    @GetMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信登录凭证(code)", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "portrait", value = "用户头像url", dataType = "string", paramType = "form")
    })
    public WebResult login(@ApiParam(name = "code", value = "微信登录code") String code, @ApiParam(value = "portrait") String portrait, HttpServletRequest httpServletRequest){
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

    @UserLoginToken
    @ApiOperation(value = "绑定微信用户登录信息")
    @PostMapping("/binding")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "姓名", required = true, paramType = "form"),
            @ApiImplicitParam(name = "stuIDCard", value = "身份证号码/电话号码", required = true, paramType = "form"),
            @ApiImplicitParam(name = "signature", value = "sha1( rawData + session_key )", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "rawData", value = "rawData", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "encryptedData", value = "加密数据", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "iv", value = "数据接口返回", dataType = "string", paramType = "form"),
    })
    public WebResult binding(@RequestBody BindingUserReq bindingUserReq, HttpServletRequest request){
        MyAssert.blank(bindingUserReq.getStuIDCard(), DefineCode.ERR0010, "身份证号码/手机号码不为空");
        MyAssert.blank(bindingUserReq.getStudentName(), DefineCode.ERR0010, "用户名不为空");
        MyAssert.isFalse(Validator.isMobile(bindingUserReq.getStuIDCard()) || IdcardUtil.isValidCard(bindingUserReq.getStuIDCard()), DefineCode.ERR0010, "身份证号码或手机号码格式不正确");
        BindingUserRequest bindingUser = new BindingUserRequest();
        BeanUtil.copyProperties(bindingUserReq, bindingUser);
        bindingUser.setOpenId(tokenService.getOpenId(request.getHeader("token")));
        return WebResult.okResult(weChatUserService.bindingUser(bindingUser));
    }

    /**
     * todo delete
     * @param studentId
     * @return
     */
    @PassToken
    @ApiOperation(value = "重置用户登陆绑定信息")
    @GetMapping("/restart/{studentId}")
    public WebResult restart(@PathVariable("studentId") String studentId){
        MyAssert.isNull(studentId, DefineCode.ERR0010, "学生id不为空");
        log.info("restart weChat studentId : [{}]", studentId);
        weChatUserService.restart(studentId);
        return WebResult.okResult();
    }
}
