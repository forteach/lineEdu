package com.project.schoolroll.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-5 17:45
 * @version: 1.0
 * @description:
 */
@Data
public class StudentVo implements Serializable {
    public String stuId;
    public String stuName;
    public String specialtyId;
    public String specialtyName;
    public String peopleId;
    public String centerId;
    public String centerName;
    public String studentCategory;
    public String classId;
    public String className;
    public String educationalSystem;
    public String waysStudy;
    public String learningModality;
    public String waysEnrollment;
    public String enrollmentDate;
    public String grade;
    public String entranceCertificateNumber;
    public String candidateNumber;
    public String totalExaminationAchievement;
}
