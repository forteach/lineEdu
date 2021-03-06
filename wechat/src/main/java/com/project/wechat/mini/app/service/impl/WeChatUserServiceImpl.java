package com.project.wechat.mini.app.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.schoolroll.domain.online.StudentOnLine;
import com.project.schoolroll.service.online.StudentOnLineService;
import com.project.schoolroll.service.online.TbClassService;
import com.project.token.service.TokenService;
import com.project.wechat.mini.app.config.WeChatMiniAppConfig;
import com.project.wechat.mini.app.domain.WeChatLog;
import com.project.wechat.mini.app.domain.WeChatUser;
import com.project.wechat.mini.app.dto.IWeChatUser;
import com.project.wechat.mini.app.repository.WeChatUserRepository;
import com.project.wechat.mini.app.service.WeChatService;
import com.project.wechat.mini.app.service.WeChatUserService;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import com.project.wechat.mini.app.web.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.project.base.common.keyword.Dic.*;
import static com.project.token.constant.TokenKey.*;

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

    private final StudentOnLineService studentOnLineService;
    private final WeChatUserRepository weChatUserRepository;
    private final WeChatService weChatService;
    private final TbClassService tbClassService;
    private final StringRedisTemplate stringRedisTemplate;
    private final TokenService tokenService;


    @Autowired
    public WeChatUserServiceImpl(WeChatService weChatService, StudentOnLineService studentOnLineService, TbClassService tbClassService,
                                 WeChatUserRepository weChatUserRepository, StringRedisTemplate stringRedisTemplate, TokenService tokenService) {
        this.weChatUserRepository = weChatUserRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenService = tokenService;
        this.weChatService = weChatService;
        this.studentOnLineService = studentOnLineService;
        this.tbClassService = tbClassService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindingUser(BindingUserRequest bindingUserReq) {
        List<StudentOnLine> list = studentOnLineService.findByStuIDCardAndStudentName(StrUtil.trim(bindingUserReq.getStuIDCard()), StrUtil.trim(bindingUserReq.getStudentName()));
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0014, "身份信息不符, 请联系管理员");
        Optional<WeChatUser> weChatUserInfoOptional = weChatUserRepository.findByOpenId(bindingUserReq.getOpenId()).stream().findFirst();
        if (weChatUserInfoOptional.isPresent() && WX_INFO_BINDIND_0.equals(weChatUserInfoOptional.get().getBinding())) {
            MyAssert.isNull(null, DefineCode.ERR0014, "该微信账号已经认证");
        }
        WeChatUser weChatUser = weChatUserInfoOptional.orElseGet(WeChatUser::new);
        StudentOnLine studentOnLine = list.get(0);
        if (checkStudent(bindingUserReq, studentOnLine.getStudentName(), studentOnLine.getStuIDCard())) {
            String openId = bindingUserReq.getOpenId();
            String key = USER_PREFIX.concat(openId);
            updateWeChatUser(key, bindingUserReq, weChatUser);
            weChatUser.setBinding(WX_INFO_BINDIND_0);
            weChatUser.setStudentId(studentOnLine.getStudentId());
            weChatUser.setClassId(studentOnLine.getClassId());
            weChatUser.setCenterAreaId(studentOnLine.getCenterAreaId());
            weChatUser.setStudentName(studentOnLine.getStudentName());
            weChatUser.setClassName(tbClassService.findClassByClassId(studentOnLine.getClassId()).getClassName());
            weChatUser.setOpenId(openId);
            weChatUser.setType(bindingUserReq.getType());
            weChatUser.setUpdateUser(bindingUserReq.getStuIDCard());
            weChatUser.setCreateUser(bindingUserReq.getStuIDCard());
            weChatUserRepository.save(weChatUser);
            return "绑定成功";
        }
        return "身份信息不符, 认证失败!";
    }

    /**
     * 绑定是教师端微信
     *
     * @param bindingUserReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindTeacher(BindingUserRequest bindingUserReq, String teacherId, String userName, String centerId, String roleCode) {
        String studentName = bindingUserReq.getStudentName();
        List<WeChatUser> weChatUsers = weChatUserRepository.findByStudentId(studentName);
        if (!weChatUsers.isEmpty()){
            WeChatUser weChatUser = weChatUsers.get(0);
            MyAssert.isTrue(WX_INFO_BINDIND_0.equals(weChatUser.getBinding()), DefineCode.ERR0010, "当前账号已经绑定过了");
        }
        String openId = bindingUserReq.getOpenId();
        WeChatUser weChatUser = weChatUserRepository.findAllByOpenId(openId).orElseGet(WeChatUser::new);
        String key = USER_PREFIX.concat(openId);
        updateWeChatUser(key, bindingUserReq, weChatUser);
        weChatUser.setOpenId(openId);
        weChatUser.setBinding(WX_INFO_BINDIND_0);
        weChatUser.setCreateUser(bindingUserReq.getStudentName());
        weChatUser.setUpdateUser(bindingUserReq.getStudentName());
        weChatUser.setStudentId(teacherId);
        weChatUser.setStudentName(userName);
        weChatUser.setCenterAreaId(centerId);
        if (USER_ROLE_CODE_TEACHER.equals(roleCode)) {
            //是教师
            weChatUser.setRoleId(WECHAT_ROLE_ID_TEACHER);
        }else if (USER_ROLE_CODE_CENTER.equals(roleCode)){
            //是学习中心
            weChatUser.setRoleId(WECHAT_ROLE_ID_CENTER);
        }else if (USER_ROLE_CODE_ADMIN.equals(roleCode)){
            //是管理员
            weChatUser.setRoleId(WECHAT_ROLE_ID_ADMIN);
        }
        weChatUserRepository.save(weChatUser);
        return "绑定成功";
    }

    private void updateWeChatUser(String key, BindingUserRequest bindingUserReq, WeChatUser weChatUser) {
        String sessionKey = tokenService.getSessionKey(key);
        final WxMaService wxService = WeChatMiniAppConfig.getMaService();
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
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse bindingToken(WxMaJscode2SessionResult session, String portrait, String ip) {
        String openId = session.getOpenid();
        String binding = WX_INFO_BINDIND_1;
        String centerAreaId = "";
        Optional<WeChatUser> weChatUserInfoOptional = weChatUserRepository.findByOpenId(openId).stream().findFirst();
        if (weChatUserInfoOptional.isPresent()) {
            WeChatUser weChatUser = weChatUserInfoOptional.get();
            MyAssert.isTrue(TAKE_EFFECT_CLOSE.equals(weChatUser.getIsValidated()), DefineCode.ERR0010, "您的信息已经失效请联系管理员");
            binding = weChatUser.getBinding();
            centerAreaId = weChatUser.getCenterAreaId();
        }
        WeChatUser weChatUser = weChatUserInfoOptional.orElseGet(WeChatUser::new);
        String token = "";
        if (WECHAT_ROLE_ID_TEACHER.equals(weChatUser.getRoleId())) {
            //是教师
            token = tokenService.createToken(openId, centerAreaId, USER_ROLE_CODE_TEACHER);
        }else if (WECHAT_ROLE_ID_CENTER.equals(weChatUser.getRoleId())){
            //是学习中心
            token = tokenService.createToken(openId, centerAreaId, USER_ROLE_CODE_CENTER);
        }else if(WECHAT_ROLE_ID_ADMIN.equals(weChatUser.getRoleId())){
            //管理员
            token = tokenService.createToken(openId, centerAreaId, USER_ROLE_CODE_ADMIN);
        } else {
            //是学生
            token = tokenService.createToken(openId, centerAreaId);
        }
        Map<String, Object> map = BeanUtil.beanToMap(weChatUser);
        map.put("openId", openId);
        map.put("sessionKey", session.getSessionKey());
        map.put("token", token);
        map.put("unionid", session.getUnionid());
        map.put("binding", binding);
        String key = USER_PREFIX.concat(openId);
        stringRedisTemplate.opsForHash().putAll(key, map);
        //设置有效期7天
        stringRedisTemplate.expire(key, TOKEN_VALIDITY_TIME, TimeUnit.SECONDS);

        if (StrUtil.isNotBlank(portrait)) {
            weChatUserInfoOptional.ifPresent(w -> {
                w.setAvatarUrl(portrait);
                w.setAvatarUrl(portrait);
                weChatUserRepository.save(w);
                String studentKey = STUDENT_ADO.concat(w.getStudentId());
                stringRedisTemplate.opsForHash().put(studentKey, "portrait", portrait);
                stringRedisTemplate.expire(studentKey, TOKEN_VALIDITY_TIME, TimeUnit.DAYS);
            });
        }
        LoginResponse loginResp = new LoginResponse();
        //获取登陆用户信息
        weChatUserRepository.findAllByIsValidatedEqualsAndOpenId(openId).ifPresent(i -> {
            String roleId = i.getRoleId();
            if (WECHAT_ROLE_ID_STUDENT.equals(roleId)) {
                //是学生登陆，查询学生的对应信息
                studentOnLineService.findById(roleId).ifPresent(s -> BeanUtil.copyProperties(s, loginResp));
            }
            loginResp.setRoleId(roleId);
            loginResp.setClassId(i.getClassId());
            loginResp.setClassName(i.getClassName());
            loginResp.setPortrait(i.getPortrait());
            loginResp.setStudentId(i.getStudentId());
            loginResp.setStudentName(i.getStudentName());
            loginResp.setCenterAreaId(i.getCenterAreaId());
            //添加异步登陆日志
            saveLoginLog(i, ip);
        });
        loginResp.setBinding(binding);
        loginResp.setToken(token);
        return loginResp;
    }

    private void saveLoginLog(IWeChatUser iWeChatUser, String ip) {
        WeChatLog weChatLog = new WeChatLog();
        weChatLog.setIp(ip);
        weChatLog.setCenterAreaId(iWeChatUser.getCenterAreaId());
        weChatLog.setUpdateUser(iWeChatUser.getStudentId());
        weChatLog.setCreateUser(iWeChatUser.getStudentId());
        weChatService.addLog(weChatLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void untying(String openId) {
        List<WeChatUser> list = weChatUserRepository.findByOpenId(openId);
        MyAssert.isTrue(list.isEmpty(), DefineCode.ERR0014, "不存要解绑的用户");
        //删除token登录信息
        list.forEach(w -> tokenService.removeToken(w.getOpenId()));
        //删除微信登录信息
        list.forEach(w -> weChatUserRepository.delete(w));
    }

    /**
     * 校验身份证和姓名在数据库中是否存在
     *
     * @param bindingUserReq
     * @return
     */
    private boolean checkStudent(BindingUserRequest bindingUserReq, String studentName, String stuIDCard) {
        return studentName.equals(bindingUserReq.getStudentName()) && stuIDCard.equals(bindingUserReq.getStuIDCard());
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

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String studentId, String status, String userId) {
        weChatUserRepository.findByStudentId(studentId)
                .forEach(w -> {
                    w.setIsValidated(status);
                    w.setUpdateUser(userId);
                    weChatUserRepository.save(w);
                    tokenService.removeToken(w.getOpenId());
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWeChatInfo(String newCenterName, String centerName, String centerId, String userId){
        List<WeChatUser> list = weChatUserRepository.findByStudentId(centerName);
        WeChatUser weChatUser = list.isEmpty() ? new WeChatUser() : list.get(0);
        weChatUser.setUpdateUser(userId);
        weChatUser.setCreateUser(userId);
        weChatUser.setStudentId(newCenterName);
        weChatUser.setStudentName(newCenterName);
        weChatUser.setCenterAreaId(centerId);
        weChatUser.setGender("1");
        weChatUser.setRoleId(WECHAT_ROLE_ID_CENTER);
        weChatUserRepository.save(weChatUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByStudentId(String studentId) {
        //移除学生对应的token
        weChatUserRepository.findByStudentId(studentId).forEach(s -> tokenService.removeToken(s.getOpenId()));
        //删除学生绑定的信息
        weChatUserRepository.deleteAllByStudentId(studentId);
    }
}