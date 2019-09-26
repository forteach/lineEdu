package com.project.user.web.vo;

import lombok.Data;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 15:40
 * @version: 1.0
 * @description:
 */
@Data
public class TeacherVerifyVo {
    private String teacherId;
    private String isValidated;
    private String remark;
}
