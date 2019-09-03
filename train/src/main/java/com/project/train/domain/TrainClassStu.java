package com.project.train.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 培训项目班级学生管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_class_stu", comment = "培训项目班级学生管理")
@Table(name = "train_class_stu", indexes = {@Index(columnList = "train_stu_id", name = "train_stu_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainClassStu extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "train_stu_id", columnDefinition = "VARCHAR(40) COMMENT '培训班级学生编号'")
    private String trainStuId;

    @Column(name = "train_class_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目班级编号'")
    private String trainClassId;

    @Column(name = "train_class_name", columnDefinition = "VARCHAR(60) COMMENT '培训班级名称'")
    private String trainClassName;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    private String pjPlanId;

    @Column(name = "train_project_name", columnDefinition = "VARCHAR(60) COMMENT '培训项目名称'")
    private String trainProjectName;

    @Column(name = "user_id", columnDefinition = "VARCHAR(60) COMMENT '系统用户编号'")
    private String userId;

    @Column(name = "gender", columnDefinition = "VARCHAR(60) COMMENT '性别'")
    private String gender;

    @Column(name = "stu_name", columnDefinition = "VARCHAR(60) COMMENT '姓名'")
    private String stuName;

//    @Column(name = "marriage", columnDefinition = "VARCHAR(60) COMMENT '民族'")
//    private String marriage;

    @Column(name = "nation", columnDefinition = "VARCHAR(60) COMMENT '民族'")
    private String nation;

    @Column(name = "job_title", columnDefinition = "VARCHAR(60) COMMENT '单位职务'")
    private String jobTitle;

    @Column(name = "stu_id_card", columnDefinition = "VARCHAR(60) COMMENT '身份证号'")
    private String stuIdCard;

    @Column(name = "stu_phone", columnDefinition = "VARCHAR(60) COMMENT '联系方式'")
    private String stuPhone;


}
