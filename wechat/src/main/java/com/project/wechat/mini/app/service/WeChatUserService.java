package com.project.wechat.mini.app.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.project.wechat.mini.app.web.request.BindingUserRequest;
import com.project.wechat.mini.app.web.request.WeChatUserRequest;
import com.project.wechat.mini.app.web.response.LoginResponse;
import com.project.wechat.mini.app.web.vo.WxDataVo;

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
    Object bindingUser(BindingUserRequest bindingUserReq);

    /**
     * 生成token并绑定用户上
     * @param session
     * @return
     */
    LoginResponse bindingToken(WxMaJscode2SessionResult session, String portrait);

    /**
     * 获取绑定手机号码信息
     */
    WxMaPhoneNumberInfo getBindingPhone(WxDataVo wxDataVo);

    /**
     * 重置微信用户绑定信息
     * @param string
     */
    void restart(String string);

    Object saveWeChatUser(WeChatUserRequest weChatUserReq);
}
