package com.project.user.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.Md5Util;
import com.project.schoolroll.domain.LearnCenter;
import com.project.schoolroll.service.LearnCenterService;
import com.project.token.service.TokenService;
import com.project.user.domain.SysUserLog;
import com.project.user.domain.SysUsers;
import com.project.user.domain.Teacher;
import com.project.user.repository.TeacherRepository;
import com.project.user.repository.UserRepository;
import com.project.user.repository.UserRoleRepository;
import com.project.user.repository.dto.SysRoleDto;
import com.project.user.service.SysUserLogService;
import com.project.user.service.UserService;
import com.project.user.web.req.RegisterUserReq;
import com.project.user.web.req.UpdatePassWordReq;
import com.project.user.web.req.UserLoginReq;
import com.project.user.web.resp.LoginResponse;
import com.project.user.web.vo.RegisterTeacherVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.token.constant.TokenKey.*;
import static java.util.stream.Collectors.toList;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-2 17:44
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * HMacMD5加密的盐
     */
    @Value("${token.salt}")
    private String salt;

    /**
     * 初始化的用户密码
     */
//    @Value("${initialization.password:123456}")
//    private String initPassWord;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private TokenService tokenService;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private SysUserLogService sysUserLogService;

    @Resource
    private LearnCenterService learnCenterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse login(UserLoginReq userLoginReq) {
        SysUsers user = userRepository.findByTeacherId(userLoginReq.getTeacherCode());
        if (user == null) {
            MyAssert.isNull(null, DefineCode.ERR0014, "用户不存在");
        } else if (TAKE_EFFECT_CLOSE.equals(user.getIsValidated())) {
            MyAssert.isNull(null, DefineCode.ERR0014, "您的账号已经失效,请联系管理员");
        } else if (!user.getPassWord().equals(Md5Util.macMD5(userLoginReq.getPassWord().concat(salt)))) {
            MyAssert.isNull(null, DefineCode.ERR0016, "密码错误");
        }
        String userId = user.getId();
        String token = tokenService.createToken(userId, user.getCenterAreaId(), user.getRoleCode());
        //保存token到redis
        Map<String, Object> map = BeanUtil.beanToMap(user);
        map.put("token", token);
        List<SysRoleDto> sysRoles = userRoleRepository.findByIsValidatedEqualsAndUserId(userId);
        LearnCenter learnCenter = learnCenterService.findByCenterId(user.getCenterAreaId());
        String centerName = learnCenter.getCenterName();
        LoginResponse loginResponse = LoginResponse.builder()
                .userId(userId)
                .token(token)
                .roleCode(user.getRoleCode())
                .userName(user.getUserName())
                .teacherId(user.getTeacherId())
                .centerAreaId(user.getCenterAreaId())
                .centerName(centerName)
                .learnCenter(learnCenter)
                .build();
        if (!sysRoles.isEmpty()) {
            sysRoles.stream().findFirst().ifPresent(sysRole -> {
                loginResponse.setRoleActivity(sysRole.getRoleActivity());
                loginResponse.setRoleId(sysRole.getRoleId());
                loginResponse.setRoleName(sysRole.getRoleName());
                map.put("roleId", sysRole.getRoleId());
                map.put("roleActivity", sysRole.getRoleActivity());
                map.put("roleName", sysRole.getRoleName());
            });
        }
        tokenService.saveRedis(USER_TOKEN_PREFIX.concat(userId), map);

        //添加登陆记录
        saveSysUserLoginLog(userLoginReq.getIp(), userId, user.getCenterAreaId());

        return loginResponse;
    }

    private void saveSysUserLoginLog(String ip, String userId, String centerAreaId) {
        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setIp(ip);
        sysUserLog.setUpdateUser(userId);
        sysUserLog.setCreateUser(userId);
        sysUserLog.setCenterAreaId(centerAreaId);
        sysUserLogService.addLog(sysUserLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerUser(RegisterUserReq registerUserReq) {
        Optional<Teacher> teacher = teacherRepository.findById(registerUserReq.getTeacherCode());
        if (!teacher.isPresent()) {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在您的信息，请联系管理员");
        }
        //验证是否注册
        SysUsers users = userRepository.findByTeacherId(registerUserReq.getTeacherCode());
        if (users != null) {
            MyAssert.isNull(null, DefineCode.ERR0011, "您已经注册过了");
        }
        SysUsers user = new SysUsers();
        user.setId(registerUserReq.getTeacherCode());
        user.setPassWord(Md5Util.macMD5(registerUserReq.getPassWord().concat(salt)));
        user.setTeacherId(registerUserReq.getTeacherCode());
        user.setUserName(registerUserReq.getUserName());
        user.setUpdateUser(registerUserReq.getTeacherCode());
        user.setCreateUser(registerUserReq.getTeacherCode());
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassWord(String id, String userId) {
        Optional<SysUsers> optionalSysUsers = userRepository.findById(id);
        MyAssert.isFalse(optionalSysUsers.isPresent(), DefineCode.ERR0014, "不存在您的信息，请联系管理员");
        SysUsers users = optionalSysUsers.get();
        String phone = users.getRegisterPhone();
        users.setPassWord(Md5Util.macMD5(StrUtil.sub(phone, phone.length() - 6, phone.length()).concat(salt)));
        users.setUpdateUser(userId);
        userRepository.save(users);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassWord(UpdatePassWordReq updatePassWordReq) {
        Optional<SysUsers> usersOptional = userRepository.findById(updatePassWordReq.getTeacherCode());
        MyAssert.isFalse(usersOptional.isPresent(), DefineCode.ERR0014, "不存在相关用户");
        SysUsers users = usersOptional.get();
        String oldPassWord = Md5Util.macMD5(updatePassWordReq.getOldPassWord().concat(salt));
        MyAssert.isFalse(oldPassWord.equals(users.getPassWord()), DefineCode.ERR0016, "旧密码不正确");
        String newPassWord = Md5Util.macMD5(updatePassWordReq.getNewPassWord().concat(salt));
        users.setPassWord(newPassWord);
        users.setUpdateUser(updatePassWordReq.getTeacherCode());
        userRepository.save(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateState(String teacherCode, String userId) {
        SysUsers users = userRepository.findByTeacherId(teacherCode);
        MyAssert.isNull(users, DefineCode.OK, "未找到要修改的用户");
        if (TAKE_EFFECT_CLOSE.equals(users.isValidated)) {
            users.setIsValidated(TAKE_EFFECT_OPEN);
        } else {
            users.setIsValidated(TAKE_EFFECT_CLOSE);
        }
        users.setUpdateUser(userId);
        userRepository.save(users);
        //移除redis 中的token 信息
        tokenService.removeToken(users.getId());
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerTeacher(RegisterTeacherVo vo) {
        String phone = vo.getPhone();
        SysUsers users = userRepository.findByTeacherId(phone);
        if (users != null) {
            MyAssert.isNull(null, DefineCode.ERR0011, "您已经注册过了");
        }
        SysUsers user = new SysUsers();
        user.setId(vo.getPhone());
        user.setRoleCode(USER_ROLE_CODE_TEACHER);
        //取手机号码后6位是初始密码
        user.setPassWord(Md5Util.macMD5(StrUtil.sub(phone, phone.length() - 6, phone.length()).concat(salt)));
        user.setTeacherId(phone);
        user.setUserName(vo.getTeacherName());
        user.setRegisterPhone(phone);
        user.setCenterAreaId(vo.getCenterAreaId());
        user.setUpdateUser(vo.getCreateUser());
        user.setCreateUser(vo.getCreateUser());
        userRepository.save(user);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacher(String phone, String newPhone, String userId) {
        userRepository.findById(phone).ifPresent(u -> {
            u.setRegisterPhone(newPhone);
            u.setId(newPhone);
            u.setTeacherId(newPhone);
            //同时修改新手机号码后6位为新密码
            u.setPassWord(Md5Util.macMD5(StrUtil.sub(newPhone, newPhone.length() - 6, newPhone.length()).concat(salt)));
            u.setUpdateUser(userId);
            userRepository.save(u);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerCenter(String centerName, String phone, String centerAreaId, String createUser) {
        Optional<SysUsers> optionalSysUsers = userRepository.findById(centerName);
        MyAssert.isTrue(optionalSysUsers.isPresent(), DefineCode.ERR0011, "您已经注册过了");
        SysUsers user = new SysUsers();
        user.setId(centerName);
        user.setRoleCode(USER_ROLE_CODE_CENTER);
        user.setPassWord(Md5Util.macMD5(StrUtil.sub(phone, phone.length() - 6, phone.length()).concat(salt)));
        user.setTeacherId(centerName);
        user.setUserName(centerName);
        user.setRegisterPhone(phone);
        user.setCenterAreaId(centerAreaId);
        user.setUpdateUser(createUser);
        user.setCreateUser(createUser);
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenterUsers(List<String> list){
        List<SysUsers> collect = userRepository.findAllById(list)
                .stream()
                .filter(Objects::nonNull)
                .peek(c -> c.setIsValidated(TAKE_EFFECT_CLOSE))
                .collect(toList());
        if (!collect.isEmpty()){
            userRepository.saveAll(collect);
        }
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(String centerName, String newCenterName, String updateUser) {
        userRepository.findById(centerName).ifPresent(s -> {
            s.setId(newCenterName);
            s.setTeacherId(newCenterName);
            s.setUserName(newCenterName);
            s.setUpdateUser(updateUser);
            userRepository.save(s);
        });
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String id, String status, String userId) {
        userRepository.findById(id).ifPresent(u -> {
            u.setIsValidated(status);
            u.setUpdateUser(userId);
            userRepository.save(u);
            tokenService.removeToken(id);
        });
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenterPhone(String centerName, String phone, String userId) {
        userRepository.findById(centerName).ifPresent(s -> {
            s.setRegisterPhone(phone);
            s.setUpdateUser(userId);
            userRepository.save(s);
        });
    }
}