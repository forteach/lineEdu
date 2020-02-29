package com.project.wechat.mini.app.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import com.project.wechat.mini.app.web.response.LoginResponse;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 19-1-10 12:05
 * @Version: 1.0
 * @Description:
 */
public interface WeChatUserService {
    /**
     * 绑定微信登录学号和 openId, 进行身份校验，通过取redis 数据库比较
     * @param bindingUserReq
     * @return WebResult
     */
    String bindingUser(BindingUserRequest bindingUserReq);

    String bindTeacher(BindingUserRequest bindingUserReq, String teacherId, String userName, String centerId, String roleCode);

    /**
     * 生成token并绑定用户上
     * @param session
     * @return
     */
    LoginResponse bindingToken(WxMaJscode2SessionResult session, String portrait, String ip);

    void updateStatus(String studentId, String status, String userId);

    /**
     * 学习中心修改微信绑定的手机号码
     * @param newCenterName 新换的手机号码
     * @param centerName 学习中心名称
     * @param userId 用户Id
     */
    void updateWeChatInfo(String newCenterName, String centerName, String centerId, String userId);

    void deleteByStudentId(String studentId);

    /** 学生端微信用户自己解绑微信登陆的帐号*/
    void untying(String openId);
}
