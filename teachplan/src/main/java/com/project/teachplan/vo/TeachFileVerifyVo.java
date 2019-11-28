package com.project.teachplan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 15:19
 * @version: 1.0
 * @description:
 */
@Data
public class TeachFileVerifyVo implements Serializable {
    private String planId;
    private String verifyStatus;
    private String remark;
    private String classId;
    private String courseId;
    private String createDate;
}