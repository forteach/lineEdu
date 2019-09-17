package com.project.user.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.Md5Util;
import com.project.token.service.TokenService;
import com.project.user.domain.SysUsers;
import com.project.user.domain.Teacher;
import com.project.user.domain.UserRole;
import com.project.user.repository.SysRoleRepository;
import com.project.user.repository.TeacherRepository;
import com.project.user.repository.UserRepository;
import com.project.user.repository.UserRoleRepository;
import com.project.user.repository.dto.SysRoleDto;
import com.project.user.service.UserService;
import com.project.user.web.req.RegisterUserReq;
import com.project.user.web.req.UpdatePassWordReq;
import com.project.user.web.req.UserLoginReq;
import com.project.user.web.resp.LoginResponse;
import com.project.user.web.vo.RegisterCenterVo;
import com.project.user.web.vo.RegisterTeacherVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;
import static com.project.token.constant.TokenKey.*;

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
    @Value("${initialization.password:123456}")
    private String initPassWord;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private TokenService tokenService;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private SysRoleRepository sysRoleRepository;

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
        String token = tokenService.createToken(user.getId(), user.getCenterAreaId());
        //保存token到redis
        Map<String, Object> map = BeanUtil.beanToMap(user);
        map.put("token", token);
        List<SysRoleDto> sysRoles = userRoleRepository.findByIsValidatedEqualsAndUserId(user.getId());
        LoginResponse loginResponse = LoginResponse.builder()
                .userId(user.getId())
                .token(token)
                .roleCode(user.getRoleCode())
                .userName(user.getUserName())
                .teacherId(user.getTeacherId())
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
        tokenService.saveRedis(USER_TOKEN_PREFIX.concat(user.getId()), map);
        return loginResponse;
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
        SysUsers sysUsers = userRepository.save(user);
        //分配角色
        sysRoleRepository.findSysRoleByRoleNameAndIsValidated("teacher", TAKE_EFFECT_OPEN).ifPresent(s -> {
            userRoleRepository.save(UserRole.builder().userId(sysUsers.getId()).roleId(s.getRoleId()).build());
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassWord(String teacherCode) {
        SysUsers users = userRepository.findByTeacherId(teacherCode);
        if (users == null) {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在您的信息，请联系管理员");
        }
        users.setPassWord(Md5Util.macMD5(initPassWord.concat(salt)));
        userRepository.save(users);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addSysTeacher(String teacherCode) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherCode);
        if (!teacherOptional.isPresent()) {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在您的信息，请联系管理员");
        }
        Teacher teacher = teacherOptional.get();
        //验证是否注册
        SysUsers users = userRepository.findByTeacherId(teacherCode);
        if (users != null) {
            MyAssert.isNull(null, DefineCode.ERR0011, "您已经注册过了");
        }
        SysUsers user = new SysUsers();
        user.setPassWord(Md5Util.macMD5(initPassWord.concat(salt)));
        user.setTeacherId(teacherCode);
        user.setId(teacherCode);
        user.setRoleCode(USER_ROLE_CODE_TEACHER);
        user.setUserName(teacher.getTeacherName());
        userRepository.save(user);
        sysRoleRepository.findSysRoleByRoleNameAndIsValidated("teacher", TAKE_EFFECT_OPEN).ifPresent(s -> {
            userRoleRepository.save(UserRole.builder().userId(user.getId()).roleId(s.getRoleId()).build());
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassWord(UpdatePassWordReq updatePassWordReq) {
        Optional<SysUsers> usersOptional = userRepository.findById(updatePassWordReq.getTeacherCode());
        if (!usersOptional.isPresent()) {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在相关用户");
        }
        SysUsers users = usersOptional.get();
        String newPassWord = Md5Util.macMD5(updatePassWordReq.getNewPassWord().concat(salt));
        if (!newPassWord.equals(users.getPassWord())) {
            MyAssert.isNull(null, DefineCode.ERR0016, "旧密码不正确");
        }
        users.setPassWord(newPassWord);
        userRepository.save(users);
        sysRoleRepository.findSysRoleByRoleNameAndIsValidated("teacher", TAKE_EFFECT_OPEN).ifPresent(s -> {
            userRoleRepository.save(UserRole.builder()
                .userId(users.getId())
                .roleId(s.getRoleId())
                .build());
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateState(String teacherCode) {
        SysUsers users = userRepository.findByTeacherId(teacherCode);
        if (users != null) {
            if (TAKE_EFFECT_CLOSE.equals(users.isValidated)) {
                users.setIsValidated(TAKE_EFFECT_OPEN);
            } else {
                users.setIsValidated(TAKE_EFFECT_CLOSE);
            }
            userRepository.save(users);
            //移除redis 中的token 信息
            tokenService.removeToken(users.getId());
        }
        MyAssert.isNull(users, DefineCode.OK, "未找到要修改的用户");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerTeacher(RegisterTeacherVo vo) {
        SysUsers users = userRepository.findByTeacherId(vo.getPhone());
        if (users != null) {
            MyAssert.isNull(null, DefineCode.ERR0011, "您已经注册过了");
        }
        SysUsers user = new SysUsers();
        user.setId(vo.getPhone());
        user.setRoleCode(USER_ROLE_CODE_TEACHER);
        user.setPassWord(Md5Util.macMD5(initPassWord.concat(salt)));
        user.setTeacherId(vo.getPhone());
        user.setUserName(vo.getUserName());
        user.setRegisterPhone(vo.getPhone());
        user.setCenterAreaId(vo.getCenterAreaId());
        SysUsers sysUsers = userRepository.save(user);
        //分配角色
        sysRoleRepository.findSysRoleByRoleNameAndIsValidated("teacher", TAKE_EFFECT_OPEN).ifPresent(s -> {
            userRoleRepository.save(UserRole.builder().userId(sysUsers.getId()).roleId(s.getRoleId()).build());
        });
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacher(String phone, String newPhone){
        userRepository.findById(phone).ifPresent(u -> {
            u.setRegisterPhone(newPhone);
            u.setId(newPhone);
            u.setTeacherId(newPhone);
            userRepository.save(u);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerCenter(String centerName, String centerAreaId) {
        Optional<SysUsers> optionalSysUsers = userRepository.findById(centerName);
        if (optionalSysUsers.isPresent()) {
            MyAssert.isNull(null, DefineCode.ERR0011, "您已经注册过了");
        }
        SysUsers user = new SysUsers();
        user.setId(centerName);
        user.setRoleCode(USER_ROLE_CODE_CENTER);
        user.setPassWord(Md5Util.macMD5(initPassWord.concat(salt)));
        user.setTeacherId(centerName);
        user.setUserName(centerName);
        user.setRegisterPhone(centerName);
        user.setCenterAreaId(centerAreaId);
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(String centerName, String newCenterName){
        userRepository.findById(centerName).ifPresent(s -> {
            s.setId(newCenterName);
            s.setTeacherId(newCenterName);
            s.setRegisterPhone(newCenterName);
            s.setUserName(newCenterName);
            userRepository.save(s);
        });
    }
}
