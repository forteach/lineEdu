package com.project.schoolroll.domain;


import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 学生信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "student", comment = "学生信息")
@Table(name = "student")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生Id(学号代码)'")
    private String studentId;
    /**
     * 专业id
     */
    @Column(name = "specialty_id", columnDefinition = "VARCHAR(32) COMMENT '专业id'")
    private String specialtyId;
    /**
     * 个人id
     */
    @Column(name = "people_id", columnDefinition = "VARCHAR(32) COMMENT '个人id'")
    private String peopleId;
    /**
     * 姓名
     */
    @Column(name = "student_name", columnDefinition = "VARCHAR(32) COMMENT '学生姓名'")
    private String studentName;
    /**
     * 学习中心编号
     */
    @Column(name = "center_id", columnDefinition = "VARCHAR(32) COMMENT '学习中心编号'")
    private String centerId;
    /**
     * 学生类别
     */
    @Column(name = "student_category", columnDefinition = "VARCHAR(32) COMMENT '学生类别'")
    private String studentCategory;
    /**
     * 班级id
     */
    @Column(name = "class_id", columnDefinition = "VARCHAR(32) COMMENT '班级id'")
    private String classId;

    /**
     * 班级名称
     */
    @Column(name = "className", columnDefinition = "VARCHAR(32) COMMENT '班级名称'")
    private String className;
    /**
     * 专业简称
     */
    @Column(name = "specialty_name", columnDefinition = "VARCHAR(32) COMMENT '专业简称'")
    private String specialtyName;
    /**
     * 学制
     * 三年制，四年制，五年制，一年制
     */
    @Column(name = "educational_system", columnDefinition = "VARCHAR(32) COMMENT '学制'")
    private String educationalSystem;
    /**
     * 就读方式/学习方式
     * 走读,住校,借宿,其它
     */
    @Column(name = "ways_study", columnDefinition = "VARCHAR(32) COMMENT '就读方式/学习方式'")
    private String waysStudy;
    /**
     * 学习形式
     * 全日制
     * 非全日制
     */
    @Column(name = "learning_modality", columnDefinition = "VARCHAR(32) COMMENT '学习形式'")
    private String learningModality;
    /**
     * 入学方式
     * 统一招生考试/普通入学
     * 保送
     * 民族班
     * 定向培养
     */
    @Column(name = "ways_enrollment", columnDefinition = "VARCHAR(32) COMMENT '入学方式'")
    private String waysEnrollment;
    /**
     * 入学时间(年/月)
     */
    @Column(name = "enrollment_date", columnDefinition = "VARCHAR(32) COMMENT '入学时间(年/月)'")
    private String enrollmentDate;
    /**
     * 年级
     */
    @Column(name = "grade", columnDefinition = "VARCHAR(32) COMMENT '年级'")
    private String grade;
    /**
     * 准考证号码
     */
    @Column(name = "entrance_certificate_number", columnDefinition = "VARCHAR(32) COMMENT '准考证号码'")
    private String entranceCertificateNumber;
    /**
     * 考生号
     */
    @Column(name = "candidate_number", columnDefinition = "VARCHAR(32) COMMENT '考生号码'")
    private String candidateNumber;
    /**
     * 考试总成绩
     */
    @Column(name = "total_examination_achievement", columnDefinition = "VARCHAR(32) COMMENT '考试总成绩'")
    private String totalExaminationAchievement;

//    @Column(name = "student_code", columnDefinition = "VARCHAR(32) COMMENT '学生学校编号'")
//    private String studentCode;

//    @Column(name = "birth_date", columnDefinition = "VARCHAR(32) COMMENT '出生日期'")
//    private String birthDate;

}