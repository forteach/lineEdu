package com.project.wechat.mini.app.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.token.service.TokenService;
import com.project.wechat.mini.app.config.WeChatMiniAppConfig;
import com.project.wechat.mini.app.domain.StudentEntitys;
import com.project.wechat.mini.app.domain.WeChatUser;
import com.project.wechat.mini.app.dto.IWeChatUser;
import com.project.wechat.mini.app.repository.StudentEntitysRepository;
import com.project.wechat.mini.app.repository.WeChatUserRepository;
import com.project.wechat.mini.app.service.WeChatUserService;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import com.project.wechat.mini.app.web.request.WeChatUserRequest;
import com.project.wechat.mini.app.web.response.LoginResponse;
import com.project.wechat.mini.app.web.vo.WxDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.project.base.common.keyword.Dic.*;
import static com.project.token.constant.TokenKey.TOKEN_STUDENT;
import static com.project.token.constant.TokenKey.TOKEN_VALIDITY_TIME;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 19-1-8 15:05
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service
public class WeChatUserServiceImpl implements WeChatUserService {

    private final StudentEntitysRepository studentEntitysRepository;

    private final WeChatUserRepository weChatUserRepository;

    private final StringRedisTemplate stringRedisTemplate;

    private final TokenService tokenService;


    @Autowired
    public WeChatUserServiceImpl(StudentEntitysRepository studentEntitysRepository,
                                 WeChatUserRepository weChatUserRepository,
                                 StringRedisTemplate stringRedisTemplate,
                                 TokenService tokenService) {
        this.studentEntitysRepository = studentEntitysRepository;
        this.weChatUserRepository = weChatUserRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object bindingUser(BindingUserRequest bindingUserReq) {
        Optional<StudentEntitys> studentEntitys = studentEntitysRepository.findByIsValidatedEqualsAndStuIDCard(TAKE_EFFECT_OPEN, bindingUserReq.getStuIDCard())
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
        if (studentEntitys.isPresent()) {
            Optional<WeChatUser> weChatUserInfoOptional = weChatUserRepository.findByOpenId(bindingUserReq.getOpenId())
                    .stream()
                    .filter(Objects::nonNull)
                    .findFirst();
            if (weChatUserInfoOptional.isPresent() && WX_INFO_BINDIND_0.equals(weChatUserInfoOptional.get().getBinding())) {
                MyAssert.isNull(null, DefineCode.ERR0014, "该微信账号已经认证");
            }
            WeChatUser weChatUser = weChatUserInfoOptional.orElseGet(WeChatUser::new);
            if (checkStudent(bindingUserReq, studentEntitys.get())) {
                final WxMaService wxService = WeChatMiniAppConfig.getMaService();
                String openId = bindingUserReq.getOpenId();
                String key = USER_PREFIX.concat(openId);
                String sessionKey = tokenService.getSessionKey(key);
                // 用户信息校验
                WxMaUserInfo wxMaUserInfo = null;
                if (checkWxInfo(sessionKey, wxService, bindingUserReq)) {
                    // 解密用户信息
                    wxMaUserInfo = wxService.getUserService().getUserInfo(sessionKey, bindingUserReq.getEncryptedData(), bindingUserReq.getIv());
                }
                // 需要更新用户数据信息
                if (wxMaUserInfo != null) {
                    BeanUtils.copyProperties(wxMaUserInfo, weChatUser);
                }
                weChatUser.setBinding(WX_INFO_BINDIND_0);
                weChatUser.setStuId(studentEntitys.get().getStuId());
                weChatUser.setClassId(studentEntitys.get().getClassId());
                weChatUser.setOpenId(openId);
                weChatUserRepository.save(weChatUser);
                //保存redis 设置有效期7天
                Map<String, Object> map = BeanUtil.beanToMap(weChatUser);
                //设置token类型为学生微信登录
                map.put("type", TOKEN_STUDENT);
                stringRedisTemplate.opsForHash().putAll(key, map);
                stringRedisTemplate.expire(key, TOKEN_VALIDITY_TIME, TimeUnit.SECONDS);
                return "绑定成功";
            }
        }
        MyAssert.isNull(null, DefineCode.ERR0014, "身份信息不符, 请联系管理员");
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse bindingToken(WxMaJscode2SessionResult session, String portrait) {
        String openId = session.getOpenid();
        String token = tokenService.createToken(openId);
        String binding = WX_INFO_BINDIND_1;

        Optional<WeChatUser> weChatUserInfoOptional = weChatUserRepository.findByOpenId(openId).stream().findFirst();
        if (weChatUserInfoOptional.isPresent()) {
            binding = weChatUserInfoOptional.get().getBinding();
        }

        Map<String, Object> map = BeanUtil.beanToMap(weChatUserInfoOptional.orElse(new WeChatUser()));
        map.put("openId", openId);
        map.put("sessionKey", openId);
        map.put("token", token);
        map.put("binding", binding);
        String key = USER_PREFIX.concat(openId);
        stringRedisTemplate.opsForHash().putAll(key, map);
        //设置有效期7天
        stringRedisTemplate.expire(key, TOKEN_VALIDITY_TIME, TimeUnit.SECONDS);

        weChatUserInfoOptional.ifPresent(weChatUser -> {
            studentEntitysRepository.findById(weChatUser.getStuId()).ifPresent(studentEntitys -> {
                if (StrUtil.isNotBlank(portrait)) {
                    weChatUser.setAvatarUrl(portrait);
                    weChatUserRepository.save(weChatUser);
                }
                studentEntitys.setPortrait(portrait);
                studentEntitysRepository.save(studentEntitys);
                String studentKey = STUDENT_ADO.concat(weChatUser.getStuId());
                stringRedisTemplate.opsForHash().put(studentKey, "portrait", portrait);
            });
        });

        //todo 获取登陆用户信息
//        IWeChatUser iWeChatUser = weChatUserRepository.findByIsValidatedEqualsAndOpenId(openId);
        LoginResponse loginResp = new LoginResponse();
//        if (iWeChatUser != null) {
//            loginResp.setClassId(iWeChatUser.getClassId());
//            loginResp.setClassName(iWeChatUser.getClassName());
//            loginResp.setPortrait(iWeChatUser.getPortrait());
//            loginResp.setStuId(iWeChatUser.getStuId());
//            loginResp.setStuName(iWeChatUser.getStuName());
//        }
        loginResp.setBinding(binding);
        loginResp.setToken(token);
        return loginResp;
    }

    @Override
    public WxMaPhoneNumberInfo getBindingPhone(WxDataVo wxDataVo) {
        final WxMaService wxService = WeChatMiniAppConfig.getMaService();
        String sessionKey = tokenService.getSessionKey(USER_PREFIX.concat(wxDataVo.getOpenId()));
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, wxDataVo.getRawData(), wxDataVo.getSignature())) {
            MyAssert.isNull(null, DefineCode.ERR0004, "user check failed");
        }
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, wxDataVo.getEncryptedData(), wxDataVo.getIv());
        return phoneNoInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restart(String string) {
        List<WeChatUser> list = weChatUserRepository.findByStuId(string);
        if (list.size() > 0) {
            list.stream().filter(Objects::nonNull)
                    .forEach(weChatUser -> {
                        weChatUserRepository.delete(weChatUser);
                    });
        } else {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存要删除的用户");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveWeChatUser(WeChatUserRequest weChatUserReq) {
        Optional<WeChatUser> optionalWeChatUserInfo = weChatUserRepository.findByOpenId(weChatUserReq.getOpenId()).stream().filter(Objects::nonNull).findFirst();
        if (optionalWeChatUserInfo.isPresent()) {
            WeChatUser weChatUser = optionalWeChatUserInfo.get();
            BeanUtil.copyProperties(weChatUserReq, weChatUser);
            Optional<StudentEntitys> studentEntitysOptional = studentEntitysRepository.findById(weChatUser.getStuId());
            if (studentEntitysOptional.isPresent()) {
                StudentEntitys studentEntitys = studentEntitysOptional.get();
                studentEntitys.setPortrait(weChatUserReq.getAvatarUrl());
                studentEntitysRepository.save(studentEntitys);
            }
            weChatUserRepository.save(weChatUser);
            return "操作成功!";
        } else {
            MyAssert.isNull(null, DefineCode.ERR0014, "用户不存在");
        }
        return null;
    }

    /**
     * 校验身份证和姓名在数据库中是否存在
     *
     * @param bindingUserInfoReq
     * @param studentEntitys
     * @return
     */
    private boolean checkStudent(BindingUserRequest bindingUserInfoReq, StudentEntitys studentEntitys) {
        return studentEntitys.getStuName().equals(bindingUserInfoReq.getStuName())
                && studentEntitys.getStuIDCard().equals(bindingUserInfoReq.getStuIDCard());
    }

    /**
     * 校验是否是微信发送的数据
     *
     * @param sessionKey
     * @param wxService
     * @param bindingUserInfoReq
     * @return
     */
    private boolean checkWxInfo(String sessionKey, WxMaService wxService, BindingUserRequest bindingUserInfoReq) {
        if (StrUtil.isNotBlank(bindingUserInfoReq.getEncryptedData())
                && StrUtil.isNotBlank(bindingUserInfoReq.getSignature())
                && StrUtil.isNotBlank(bindingUserInfoReq.getIv())
                && StrUtil.isNotBlank(bindingUserInfoReq.getRawData())) {
            return wxService.getUserService().checkUserInfo(sessionKey,
                    bindingUserInfoReq.getRawData(), bindingUserInfoReq.getSignature());
        }
        return false;
    }
}
