package com.project.user.service;

import com.project.user.domain.SysUsers;
import com.project.user.web.req.UpdatePassWordReq;
import com.project.user.web.req.UserLoginReq;
import com.project.user.web.resp.LoginResponse;
import com.project.user.web.vo.RegisterTeacherVo;

import java.util.List;

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


    SysUsers checkUserNameAndPassWord(String teacherCode, String passWord);

//    /**
//     * 教师端用户注册
//     *
//     * @param registerUserReq
//     * @return
//     */
//    boolean registerUser(RegisterUserReq registerUserReq);

    /**
     * 重置用户密码
     *
     * @param teacherCode
     * @return
     */
    boolean resetPassWord(String teacherCode, String userId);

    /**
     * 添加教师用户信息
     * @param teacherCode
     * @return
     */
//    boolean addSysTeacher(String teacherCode, String userId);

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
    void updateState(String teacherCodeStr, String userId);

    void registerTeacher(RegisterTeacherVo vo);

    void updateTeacher(String phone, String newPhone, String userId);

    void registerCenter(String centerName, String phone, String centerAreaId, String userId);

    void updateCenter(String center, String newCenter, String userId);

    void updateStatus(String id, String status, String userId);

    void updateCenterPhone(String centerName, String phone, String userId);

    /**
     * 批量修改管理端用户状态为无效
     */
    void updateCenterUsers(List<String> list);
}