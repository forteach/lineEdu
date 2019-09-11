package com.project.user.service;

import com.project.user.web.req.RegisterUserReq;
import com.project.user.web.req.UpdatePassWordReq;
import com.project.user.web.req.UserLoginReq;
import com.project.user.web.resp.LoginResponse;
import com.project.user.web.vo.RegisterCenterVo;
import com.project.user.web.vo.RegisterTeacherVo;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-2 17:44
 * @Version: 1.0
 * @Description:
 */
public interface UserService {

    /**
     * 教师端用户登录
     *
     * @param userLoginReq
     * @return
     */

    LoginResponse login(UserLoginReq userLoginReq);

    /**
     * 教师端用户注册
     * @param registerUserReq
     * @return
     */
    boolean registerUser(RegisterUserReq registerUserReq);

    /**
     * 重置用户密码
     * @param teacherCode
     * @return
     */
    boolean resetPassWord(String teacherCode);

    /**
     * 添加教师用户信息
     * @param teacherCode
     * @return
     */
    boolean addSysTeacher(String teacherCode);

    /**
     * 修改密码
     *
     * @param updatePassWordReq
     * @return
     */
    void updatePassWord(UpdatePassWordReq updatePassWordReq);

    /**
     * 修改教师状态使失效
     *
     * @param teacherCodeStr
     */
    void updateState(String teacherCodeStr);

    void registerTeacher(RegisterTeacherVo vo);

    void updateTeacher(String phone, String newPhone);

    void registerCenter(String centerName);

    void updateCenter(String center, String newCenter);
}