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
 * 培训项目班级管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_class", comment = "培训项目班级管理")
@Table(name = "train_class")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainClass extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "train_class_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目班级编号'")
    private String trainClassId;

    @Column(name = "train_class_name", columnDefinition = "VARCHAR(60) COMMENT '培训班级名称'")
    private String trainClassName;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'", insertable = false, updatable = false)
    private String pjPlanId;

    @Column(name = "class_admin", columnDefinition = "VARCHAR(60) COMMENT '培训班级管理员'")
    private String classAdmin;

    @Column(name = "class_admin_tel", columnDefinition = "VARCHAR(60) COMMENT '培训班级管理员电话'")
    private String classAdminTel;

    @Column(name = "line_noline", columnDefinition = "VARCHAR(60) COMMENT '线上线下'")
    private String lineNoline;

    public TrainClass(String trainClassId, String trainClassName,String pjPlanId, String classAdmin, String classAdminTel, String lineNoline, String centerId) {
        this.trainClassId = trainClassId;
        this.trainClassName = trainClassName;
        this.classAdmin = classAdmin;
        this.classAdminTel = classAdminTel;
        this.lineNoline = lineNoline;
        this.pjPlanId=pjPlanId;
        super.centerAreaId=centerId;
    }

}
