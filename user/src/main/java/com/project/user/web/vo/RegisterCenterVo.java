package com.project.user.web.vo;

import lombok.Builder;
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
@Builder
public class RegisterCenterVo implements Serializable {
//    private String email;
    private String centerName;
//    private String teacherName;
//    private String teacherId;
//    private String userName;
}