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
 * 培训项目计划列表管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_project_plan", comment = "培训项目管理")
@Table(name = "train_project_plan", indexes = {@Index(columnList = "pj_plan_id", name = "pj_plan_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainProjectPlan extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    private String pjPlanId;

    @Column(name = "train_project_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目编号'")
    private String trainProjectId;

    @Column(name = "train_project_name", columnDefinition = "VARCHAR(60) COMMENT '培训项目名称'")
    private String trainProjectName;

    @Column(name = "train_area_id", columnDefinition = "VARCHAR(60) COMMENT '培训项目领域'")
    private String trainAreaId;

    @Column(name = "apply_start", columnDefinition = "VARCHAR(60) COMMENT '报名开始时间'")
    private String applyStart;

    @Column(name = "apply_end", columnDefinition = "VARCHAR(60) COMMENT '报名结束时间'")
    private String applyEnd;

    @Column(name = "train_start", columnDefinition = "VARCHAR(60) COMMENT '计划开始执行时间'")
    private String trainStart;

    @Column(name = "train_end", columnDefinition = "VARCHAR(60) COMMENT '计划执行结束时间'")
    private String trainEnd;

    //冗余字段，计划课程
    @Column(name = "train_course", columnDefinition = "VARCHAR(800) COMMENT '计划课程编号'")
    private String trainCourse;

    @Column(name = "train_admin", columnDefinition = "VARCHAR(60) COMMENT '计划负责人'")
    private String trainAdmin;
}