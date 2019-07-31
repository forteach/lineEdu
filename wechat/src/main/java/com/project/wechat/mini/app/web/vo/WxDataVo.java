package com.project.wechat.mini.app.web.vo;

import com.project.wechat.mini.app.web.request.WeChatBaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-2-18 13:50
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxDataVo extends WeChatBaseRequest {
    private String signature;
    private String rawData;
    private String encryptedData;
    private String iv;
}
