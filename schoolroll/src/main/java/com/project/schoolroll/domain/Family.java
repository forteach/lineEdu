//package com.project.schoolroll.domain;
//
//import com.project.mysql.domain.Entitys;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-2 18:31
// * @version: 1.0
// * @description: 学生家庭成员信息
// */
//@Data
//@Entity
//@DynamicUpdate
//@DynamicInsert
//@org.hibernate.annotations.Table(appliesTo = "family", comment = "家庭成员")
//@Table(name = "family", indexes = {
//        @Index(columnList = "family_id", name = "family_id_index"),
//        @Index(columnList = "student_id", name = "student_id_index")
//})
//@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
//@NoArgsConstructor
//public class Family extends Entitys implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 家庭成员id
//     */
//    @Id
//    @Column(name = "family_id", columnDefinition = "VARCHAR(32) COMMENT '家庭成员id'")
//    private String familyId;
//    /**
//     * 学生id
//     */
//    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'")
//    private String studentId;
//    /**
//     * 姓名
//     */
//    @Column(name = "family_name", columnDefinition = "VARCHAR(32) COMMENT '姓名'")
//    private String familyName;
//    /**
//     * 家庭成员关系
//     * 父亲，母亲，其他亲属
//     */
//    @Column(name = "family_relationship", columnDefinition = "VARCHAR(32) COMMENT '家庭成员关系'")
//    private String familyRelationship;
//    /**
//     * 联系电话
//     */
//    @Column(name = "family_phone", columnDefinition = "VARCHAR(32) COMMENT '联系电话'")
//    private String familyPhone;
//    /**
//     * 是否是监护人
//     */
//    @Column(name = "is_guardian", columnDefinition = "VARCHAR(2) COMMENT '是否是监护人(Y/N)'")
//    private String isGuardian;
//    /**
//     * 身份证件类型
//     */
//    @Column(name = "family_card_type", columnDefinition = "VARCHAR(32) COMMENT '身份证件类型'")
//    private String familyCardType;
//    /**
//     * 身份证号
//     */
//    @Column(name = "family_id_card", columnDefinition = "VARCHAR(32) COMMENT '身份证号'")
//    private String familyIDCard;
//    /**
//     * 出生日期
//     */
//    @Column(name = "family_birth_date", columnDefinition = "VARCHAR(32) COMMENT '出生日期'")
//    private String familyBirthDate;
//    /**
//     * 健康状态
//     * 健康/良好
//     * 一般/较弱
//     * 残疾
//     */
//    @Column(name = "family_health_condition", columnDefinition = "VARCHAR(32) COMMENT '健康状态'")
//    private String familyHealthCondition;
//    /**
//     * 工作单位
//     */
//    @Column(name = "family_company_organization", columnDefinition = "VARCHAR(32) COMMENT '工作单位'")
//    private String familyCompanyOrganization;
//    /**
//     * 政治面貌
//     */
//    @Column(name = "family_political_status", columnDefinition = "VARCHAR(32) COMMENT '政治面貌'")
//    private String familyPoliticalStatus;
//    /**
//     * 家庭成员民族
//     */
//    @Column(name = "family_nation", columnDefinition = "VARCHAR(32) COMMENT '民族'")
//    private String familyNation;
//    /**
//     * 家庭成员职务
//     */
//    @Column(name = "family_position", columnDefinition = "VARCHAR(32) COMMENT '职务'")
//    private String familyPosition;
//}
