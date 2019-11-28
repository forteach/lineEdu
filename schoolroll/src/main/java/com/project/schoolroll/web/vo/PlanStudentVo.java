package com.project.schoolroll.web.vo;

import lombok.Data;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 15:05
 * @version: 1.0
 * @description:
 */
@Data
public class PlanStudentVo {
    public String studentId;
    public String studentName;


    public PlanStudentVo(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}