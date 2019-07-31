package com.project.portal.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.response.WebResult;
import com.project.portal.wechat.req.BindingUserReq;
import com.project.portal.wechat.req.WeChatUserReq;
import com.project.token.annotation.UserLoginToken;
import com.project.token.service.TokenService;
import com.project.wechat.mini.app.config.WeChatMiniAppConfig;
import com.project.wechat.mini.app.service.WeChatUserService;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import com.project.wechat.mini.app.web.request.WeChatUserRequest;
import com.project.wechat.mini.app.web.vo.WxDataVo;
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

    @ApiOperation(value = "微信小程序登录接口")
    @GetMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信登录凭证(code)", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "portrait", value = "用户头像url", dataType = "string", paramType = "form")
    })
    public WebResult login(@ApiParam(name = "code", value = "微信登录code") String code, @ApiParam(value = "portrait") String portrait){
        MyAssert.blank(code, DefineCode.ERR0010, "code is null");
        final WxMaService wxService = WeChatMiniAppConfig.getMaService();
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return WebResult.okResult(weChatUserService.bindingToken(session, portrait));
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return WebResult.failException(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "signature", value = "sha1( rawData + session_key )", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rawData", value = "rawData", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "encryptedData", value = "加密数据", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "数据接口返回", dataType = "string", required = true, paramType = "query"),
    })
    @UserLoginToken
    @PostMapping("/phone")
    @ApiOperation(value = "获取用户绑定手机号信息")
    public WebResult getBingPhone(@RequestBody WxDataVo wxDataVo, HttpServletRequest request){
        // 绑定手机
        wxDataVo.setOpenId(tokenService.getOpenId(request.getHeader("token")));
        return WebResult.okResult(weChatUserService.getBindingPhone(wxDataVo));
    }

    @UserLoginToken
    @ApiOperation(value = "绑定微信用户登录信息")
    @PostMapping("/binding")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuName", value = "学生身份证姓名", required = true, paramType = "form"),
            @ApiImplicitParam(name = "stuIDCard", value = "学生身份证号码", required = true, paramType = "form"),
            @ApiImplicitParam(name = "signature", value = "sha1( rawData + session_key )", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "rawData", value = "rawData", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "encryptedData", value = "加密数据", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "iv", value = "数据接口返回", dataType = "string", paramType = "form"),
    })
    public WebResult binding(@RequestBody BindingUserReq bindingUserReq, HttpServletRequest request){
        MyAssert.blank(bindingUserReq.getStuIDCard(), DefineCode.ERR0010, "身份证号码不为空");
        MyAssert.blank(bindingUserReq.getStuName(), DefineCode.ERR0010, "用户名不为空");
        BindingUserRequest bindingUser = new BindingUserRequest();
        BeanUtil.copyProperties(bindingUserReq, bindingUser);
        bindingUser.setOpenId(tokenService.getOpenId(request.getHeader("token")));
        return WebResult.okResult(weChatUserService.bindingUser(bindingUser));
    }

    /**
     * todo delete
     * @param stuId
     * @return
     */
    @ApiOperation(value = "重置用户登陆绑定信息")
    @GetMapping("/restart/{stuId}")
    public WebResult restart(@PathVariable("stuId") String stuId){
        weChatUserService.restart(stuId);
        return WebResult.okResult();
    }

    @UserLoginToken
    @PostMapping("/saveWeUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "微信昵称", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "avatarUrl", value = "用户头像图片的 URL", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别 0：未知、1：男、2：女", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "province", value = "用户所在省份", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "city", value = "用户所在城市", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "country", value = "用户所在国家", dataType = "string", paramType = "form")
    })
    public WebResult weChatUser(@RequestBody WeChatUserReq weChatUserReq, HttpServletRequest request){
        WeChatUserRequest weChatUser = new WeChatUserRequest();
        BeanUtil.copyProperties(weChatUserReq, weChatUser);
        weChatUserReq.setOpenId(tokenService.getOpenId(request.getHeader("token")));
        return WebResult.okResult(weChatUserService.saveWeChatUser(weChatUser));
    }
}
