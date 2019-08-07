package com.project.user.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-2-22 14:38
 * @version: 1.0
 * @description:
 */
@Data
public class RegisterUserReq implements Serializable {

    private String userName;

    private String passWord;

    private String teacherCode;
}