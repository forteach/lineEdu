//package com.project.schoolroll.domain;
//
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
// * 学籍信息
// */
//@Data
//@Entity
//@DynamicUpdate
//@DynamicInsert
//@org.hibernate.annotations.Table(appliesTo = "school_roll", comment = "学籍信息")
//@Table(name = "school_roll", indexes = {@Index(columnList = "roll_id", name = "roll_id_index")})
//@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
//@NoArgsConstructor
//public class SchoolRoll extends Entitys implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 学籍id
//     */
//    @Id
//    @Column(name = "roll_id", columnDefinition = "VARCHAR(32) COMMENT '学籍ID'")
//    private String rollId;
//
//    /**
//     * 学籍编号
//     */
//    @Column(name = "roll_code", columnDefinition = "VARCHAR(32) COMMENT '学籍学校编号'")
//    private String rollCode;
//
//    public SchoolRoll(String rollId, String rollCode, String centerId) {
//        this.rollId = rollId;
//        this.rollCode = rollCode;
//        super.centerAreaId=centerId;
//    }
//}
