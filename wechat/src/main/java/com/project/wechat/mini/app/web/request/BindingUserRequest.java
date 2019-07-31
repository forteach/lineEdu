package com.project.wechat.mini.app.web.request;

import com.project.wechat.mini.app.web.vo.WxDataVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 19-1-8 15:29
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BindingUserRequest extends WxDataVo implements Serializable {

    private String stuName;

    private String stuIDCard;
}
