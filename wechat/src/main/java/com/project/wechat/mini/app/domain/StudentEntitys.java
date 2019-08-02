package com.project.wechat.mini.app.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/31 22:14
 * @Version: 1.0
 * @Description:
 */
//@Data
//@Entity
//@Table(name = "v_wx_student")
//@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
//@NoArgsConstructor
//@org.hibernate.annotations.Table(appliesTo = "v_wx_student", comment = "学生信息视图")
//public class StudentEntitys extends Entitys implements Serializable {
//    @Id
//    @Column(name = "stu_id", columnDefinition = "VARCHAR(32) COMMENT '学号id'")
//    private String stuId;
//
//    @Column(name = "stu_name", columnDefinition = "VARCHAR(32) COMMENT '用户名'")
//    private String stuName;
//
//    @Column(name = "stu_id_card", columnDefinition = "VARCHAR(32) COMMENT '身份证号码'")
//    private String stuIDCard;

//    @Column(name = "portrait", columnDefinition = "VARCHAR(255) COMMENT '学生头像url'")
//    private String portrait;

//    @Column(name = "class_id", columnDefinition = "VARCHAR(32) COMMENT '学生所属班级id'")
//    private String classId;

//}