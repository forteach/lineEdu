package com.project.user.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-4 14:14
 * @version: 1.0
 * @description:
 */
@Data
public class UpdatePassWordReq implements Serializable {

    private String teacherCode;

    private String oldPassWord;

    private String newPassWord;
}