package com.project.schoolroll.domain.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-7 14:24
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_student_export")
@org.hibernate.annotations.Table(appliesTo = "v_student_export", comment = "查询学生信息关联表需要导出视图")
public class ViewStudentExport implements Serializable {

    @Id
    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生Id(学号代码)'")
    private String studentId;
    /**
     * 专业id
     */
    @Column(name = "specialty_id", columnDefinition = "VARCHAR(32) COMMENT '专业id'")
    private String specialtyId;
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

    /* ----------------------------------------------*/

    /**
     * 姓名拼音
     */
    @Column(name = "name_pin_yin", columnDefinition = "VARCHAR(64) COMMENT '姓名拼音'")
    private String namePinYin;
    /**
     * 性别
     * 男/女
     */
    @Column(name = "gender", columnDefinition = "VARCHAR(32) COMMENT '性别'")
    private String gender;
    /**
     * 身份证件类型
     * 居民身份证
     * 户口薄
     * 护照
     * 其他
     */
    @Column(name = "stu_card_type", columnDefinition = "VARCHAR(32) DEFAULT '身份证' COMMENT '身份证件类型'")
    private String stuCardType;
    /**
     * 身份证号
     */
    @Column(name = "stu_id_card", columnDefinition = "VARCHAR(32) COMMENT '身份证号'")
    private String stuIDCard;
    /**
     * 联系电话
     */
    @Column(name = "stu_phone", columnDefinition = "VARCHAR(32) COMMENT '联系电话'")
    private String stuPhone;
    /**
     * 出生日期
     */
    @Column(name = "stu_birth_date", columnDefinition = "VARCHAR(32) COMMENT '出生日期'")
    private String stuBirthDate;
    /**
     * 国籍/地区
     * 中国
     *
     */
    @Column(name = "nationality", columnDefinition = "VARCHAR(32) COMMENT '国籍/地区'")
    private String nationality;
    /**
     * 港澳台侨外类型
     * 非港澳台侨
     * 外国人
     * 其他
     */
    @Column(name = "nationality_type", columnDefinition = "VARCHAR(32) COMMENT '港澳台侨外类型'")
    private String nationalityType;
    /**
     * 婚姻状态
     * 未婚/已婚
     */
    @Column(name = "marriage", columnDefinition = "VARCHAR(32) COMMENT '婚姻状态'")
    private String marriage;
    /**
     * 民族
     * 汉族
     * 满族
     * 其他
     */
    @Column(name = "nation", columnDefinition = "VARCHAR(32) COMMENT '民族'")
    private String nation;
    /**
     * 政治面貌
     * 群众
     * 中国共产党党员
     * 无党派人士
     */
    @Column(name = "political_status", columnDefinition = "VARCHAR(32) COMMENT '政治面貌'")
    private String politicalStatus;
    /**
     * 户口/户籍性质
     * 农业户口
     * 非农业户口
     */
    @Column(name = "household_type", columnDefinition = "VARCHAR(32) COMMENT '户口/户籍性质'")
    private String householdType;
    /**
     * 是否随迁子女
     */
    @Column(name = "is_immigrant_children", columnDefinition = "VARCHAR(32) COMMENT '是否随迁子女'")
    private String isImmigrantChildren;
    /**
     * 备注
     */
    @Column(name = "remark", columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
    /**
     * 考试地区市县
     */
    @Column(name = "examinationArea", columnDefinition = "VARCHAR(32) COMMENT '考试地区(市县)'")
    private String examinationArea;
    /**
     * 入学报道日期
     */
    @Column(name = "arrival_date", columnDefinition = "VARCHAR(32) COMMENT '入学报到日期'")
    private String arrivalDate;
    /**
     * 招生批次 春季,秋季
     */
    @Column(name = "recruit_batch", columnDefinition = "VARCHAR(32) COMMENT '招生批次'")
    private String recruitBatch;
}