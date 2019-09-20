package com.project.user.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 18:14
 * @version: 1.0
 * @description:
 */
@Data
public class RegisterTeacherVo implements Serializable {
    private String email;
    private String phone;
    private String teacherName;
    private String teacherId;
    private String userName;
    private String centerAreaId;
    private String createUser;
}