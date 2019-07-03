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
import javax.print.attribute.standard.MediaSize;
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
    @Column(name = "card_type", columnDefinition = "VARCHAR(32) COMMENT '身份证件类型'")
    private String cardType;
    /**
     * 身份证号
     */
    @Column(name = "id_card", columnDefinition = "VARCHAR(32) COMMENT '身份证号'")
    private String IDCard;
    /**
     * 联系电话
     */
    @Column(name = "phone", columnDefinition = "VARCHAR(32) COMMENT '电话'")
    private String phone;
    /**
     * 出生日期
     */
    @Column(name = "birth_date", columnDefinition = "VARCHAR(32) COMMENT '出生日期'")
    private String birthDate;
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

}
