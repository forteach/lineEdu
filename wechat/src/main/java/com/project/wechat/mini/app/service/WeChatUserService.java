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

    /**
     * 生成token并绑定用户上
     * @param session
     * @return
     */
    LoginResponse bindingToken(WxMaJscode2SessionResult session, String portrait, String ip);

    /**
     * 重置微信用户绑定信息
     * @param string
     */
    void restart(String string);

    void updateStatus(String studentId, String status, String userId);

    /**
     * 注册微信教师登录信息
     * @param phone 教师注册电话
     * @param gender 教师性别
     * @param userId 审核人id
     */
    void saveTeacher(String phone, String teacherName, String gender, String centerId, String userId);
}
