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
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 17:07
 * @version: 1.0
 * @description: 学生个人信息
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "student_people")
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "student_people", comment = "学生个人信息")
@AllArgsConstructor
@NoArgsConstructor
public class StudentPeople extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个人id
     */
    @Id
    @Column(name = "people_id", columnDefinition = "VARCHAR(32) COMMENT '个人id'")
    private String peopleId;
    /**
     * 姓名
     */
    @Column(name = "stu_name", columnDefinition = "VARCHAR(32) COMMENT '学生姓名'")
    private String stuName;
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
//    /**
//     * 毕业学校
//     */
//    @Column(name = "school", columnDefinition = "VARCHAR(64) COMMENT '毕业学校'")
//    private String school;
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
//    /**
//     * 学生邮箱
//     */
//    @Column(name = "stu_email", columnDefinition = "VARCHAR(128) COMMENT '学生邮箱'")
//    private String stuEmail;

//    @Column(name = "health_condition", columnDefinition = "VARCHAR(32) COMMENT '健康状态'")
//    private String healthCondition;

}
