//package com.project.schoolroll.domain;
//
//import com.project.mysql.domain.Entitys;
//import lombok.*;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-30 15:52
// * @version: 1.0
// * @description:
// */
//@Data
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@org.hibernate.annotations.Table(appliesTo = "student_expand_dictionary", comment = "学生扩展字段健值说明")
//@Table(name = "student_expand_dictionary", indexes = {@Index(columnList = "dic_name", name = "dic_name_index")})
//@EqualsAndHashCode(callSuper = true)
//public class StudentExpandDictionary extends Entitys implements Serializable {
//
//    @Id
//    @Column(name = "dic_name", columnDefinition = "VARCHAR(128) COMMENT '字典名称'")
//    private String dicName;
//
//    @Column(name = "dic_explain", columnDefinition = "VARCHAR(32) COMMENT '字段说明'")
//    private String dicExplain;
//}
