package com.project.train.domain;

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
 * 培训项目班级学生管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_class_stu", comment = "培训项目班级学生管理")
@Table(name = "train_class_stu")
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

    @Column(name = "user_id", columnDefinition = "VARCHAR(60) COMMENT '系统用户编号'")
    private String userId;

    @Column(name = "gender", columnDefinition = "VARCHAR(60) COMMENT '性别'")
    private String gender;

    @Column(name = "stu_name", columnDefinition = "VARCHAR(60) COMMENT '姓名'")
    private String stuName;

    @Column(name = "marriage", columnDefinition = "VARCHAR(60) COMMENT '民族'")
    private String marriage;

    @Column(name = "job_title", columnDefinition = "VARCHAR(60) COMMENT '单位职务'")
    private String jobTitle;

    @Column(name = "stu_id_card", columnDefinition = "VARCHAR(60) COMMENT '身份证号'")
    private String stuIdCard;

    @Column(name = "stu_phone", columnDefinition = "VARCHAR(60) COMMENT '联系方式'")
    private String stuPhone;

    public TrainClassStu(String trainStuId, String trainClassId, String trainClassName, String userId, String gender, String stuName, String marriage, String jobTitle, String stuIdCard, String stuPhone,String centerId) {
        this.trainStuId = trainStuId;
        this.trainClassId = trainClassId;
        this.trainClassName = trainClassName;
        this.userId = userId;
        this.gender = gender;
        this.stuName = stuName;
        this.marriage = marriage;
        this.jobTitle = jobTitle;
        this.stuIdCard = stuIdCard;
        this.stuPhone = stuPhone;
        super.centerAreaId=centerId;
    }
}
