package com.project.user.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/7 12:07
 * @Version: 1.0
 * @Description:
 */
@Data
public class SysUserEditReq implements Serializable {

    private String id;

    private String registerPhone;

    private String email;
}