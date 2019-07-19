package com.project.schoolroll.domain.excel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-18 15:29
 * @version: 1.0
 * @description:
 */
@Data
public class StudentInfo implements Serializable {
    /**
     * 学生学号id
     */
    private String stuId;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 学生身份证号码
     */
    private String stuIDCard;
    /**
     * 入学年度
     */
    private String enrollmentDate;
    /**
     * 专业名称
     */
    private String specialtyName;
    /**
     * 政治面貌
     */
    private String politicalStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 学制及计划类别
     */
    private String educationalSystem;
    /**
     * 中招考试成绩/考试成绩
     */
    private String totalExaminationAchievement;
    /**
     * 准考证号
     */
    private String entranceCertificateNumber;
    /**
     * 家长姓名
     */
    private String familyName;
    /**
     * 家庭电话
     */
    private String familyPhone;
    /**
     * 工作单位
     */
    private String companyOrganization;
    /**
     * 学生电话
     */
    private String stuPhone;
    /**
     * 学生邮箱
     */
    private String stuEmail;
    /**
     * 家庭住址
     */
    private String familyAddress;
    /**
     * 备注
     */
    private String remark;
}
