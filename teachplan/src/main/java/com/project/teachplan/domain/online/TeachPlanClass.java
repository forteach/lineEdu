package com.project.teachplan.domain.online;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 培训项目班级管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "teach_plan_class", indexes = {
        @Index(columnList = "class_id", name = "class_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@org.hibernate.annotations.Table(appliesTo = "teach_plan_class", comment = "在线教学计划班级管理")
@EqualsAndHashCode(callSuper = true)
@IdClass(TeachPlanClassPk.class)
@AllArgsConstructor
public class TeachPlanClass extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private TeachPlanClassPk teachPlanClassPk;

    private String classId;

    private String planId;

    @Column(name = "class_name", columnDefinition = "VARCHAR(60) COMMENT '班级名称'")
    private String className;

    @Column(name = "plan_name", columnDefinition = "VARCHAR(100) COMMENT '计划名称'")
    private String planName;

    @Column(name = "classNumber", columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '班级人数'")
    private Integer classNumber;

    public TeachPlanClass() {
    }

    public TeachPlanClass(String classId, String planId, String className, String planName, int classNumber) {
        this.classId = classId;
        this.planId = planId;
        this.className = className;
        this.planName = planName;
        this.classNumber = classNumber;
    }
}
