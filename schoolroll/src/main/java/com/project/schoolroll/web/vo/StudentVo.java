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
    /**
     * 学生id
     */
    public String stuId;
    /**
     * 学生名字
     */
    public String stuName;
    /**
     * 专业id
     */
    public String specialtyId;
    /**
     * 专业名称
     */
    public String specialtyName;
    /**
     * 个人信息id
     */
    public String peopleId;
    /**
     * 学习中心id
     */
    public String centerId;
    /**
     * 学习中心名称
     */
    public String centerName;
    /**
     * 学习类别
     */
    public String studentCategory;
    /**
     * 班级id
     */
    public String classId;
    /**
     * 班级名称
     */
    public String className;
    /**
     * 学制 三年制，四年制，五年制，一年制
     */
    public String educationalSystem;
    /**
     * 就读方式/学习方式
     */
    public String waysStudy;
    /**
     * 学习形式
     */
    public String learningModality;
    /**
     * 入学方式
     */
    public String waysEnrollment;
    /**
     * 入学日期
     */
    public String enrollmentDate;
    /**
     * 年级
     */
    public String grade;
    /**
     * 准考证号码
     */
    public String entranceCertificateNumber;
    /**
     * 考生号
     */
    public String candidateNumber;
    /**
     * 考试总成绩
     */
    public String totalExaminationAchievement;
}