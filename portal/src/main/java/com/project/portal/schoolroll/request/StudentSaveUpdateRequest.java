package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:27
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "保存修改学生信息")
public class StudentSaveUpdateRequest implements Serializable {
    private String stuId;
    private String stuName;
    private String centerId;
    private String educationalSystem;
    private String waysStudy;
    private String learningModality;
    private String waysEnrollment;
    private String enrollmentDate;
}
