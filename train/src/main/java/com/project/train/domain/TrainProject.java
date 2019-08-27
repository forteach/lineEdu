//package com.project.train.domain;
//
//import com.project.mysql.domain.Entitys;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.io.Serializable;
//
///**
// * 培训项目列表管理
// */
//@Data
//@Entity
//@DynamicUpdate
//@DynamicInsert
//@org.hibernate.annotations.Table(appliesTo = "train_project", comment = "培训项目管理")
//@Table(name = "train_project")
//@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
//@NoArgsConstructor
//public class TrainProject extends Entitys implements Serializable {
//    private static final long serialVersionUID = 1L;
//    @Id
//    @Column(name = "train_project_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目编号'")
//    private String trainProjectId;
//
//    @Column(name = "train_project_name", columnDefinition = "VARCHAR(60) COMMENT '培训项目名称'")
//    private String trainProjectName;
//
//    @Column(name = "train_area_id", columnDefinition = "VARCHAR(60) COMMENT '培训项目领域'")
//    private String trainAreaId;
//
//    public TrainProject(String trainProjectId, String trainProjectName, String trainAreaId,String centerId) {
//        this.trainProjectId = trainProjectId;
//        this.trainProjectName = trainProjectName;
//        this.trainAreaId = trainAreaId;
//        super.centerAreaId=centerId;
//    }
//}
