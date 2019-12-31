package com.project.portal.wechat.req;

import com.project.wechat.mini.app.web.vo.WxDataVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "绑定学生用户登录微信信息")
public class BindingUserReq extends WxDataVo implements Serializable {

    @ApiModelProperty(value = "用户名", name = "studentName")
    private String studentName;

    @ApiModelProperty(value = "身份证号码/手机电话号码", name = "stuIDCard")
    private String stuIDCard;
}