package com.project.user.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-2-22 13:47
 * @version: 1.0
 * @description:
 */
@Data
public class UserLoginReq implements Serializable {

    private String teacherCode;

    private String passWord;
}