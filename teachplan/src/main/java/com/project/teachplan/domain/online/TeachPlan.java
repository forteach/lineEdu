package com.project.teachplan.domain.online;

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
@org.hibernate.annotations.Table(appliesTo = "teach_plan", comment = "在线培训项目管理")
@Table(name = "teach_plan", indexes = {@Index(columnList = "plan_id", name = "plan_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeachPlan extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "plan_id", columnDefinition = "VARCHAR(32) COMMENT '计划编号'")
    private String planId;

    @Column(name = "plan_name", columnDefinition = "VARCHAR(60) COMMENT '计划名称'")
    private String planName;

    @Column(name = "start_date", columnDefinition = "VARCHAR(60) COMMENT '计划开始执行时间'")
    private String startDate;

    @Column(name = "end_date", columnDefinition = "VARCHAR(60) COMMENT '计划执行结束时间'")
    private String endDate;

//    @Column(name = "plan_course", columnDefinition = "VARCHAR(800) COMMENT '计划课程编号'")
//    private String planCourse;

    @Column(name = "plan_admin", columnDefinition = "VARCHAR(60) COMMENT '计划负责人'")
    private String planAdmin;

//    @Column(name = "classIds", columnDefinition = "VARCHAR(800) COMMENT '班级集合id'")
//    private String classIds;

    @Column(name = "course_number", columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '计划的课程数量'")
    private Integer courseNumber;

    @Column(name = "class_number", columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '计划的班级数'")
    private int classNumber;

    @Column(name = "sum_number", columnDefinition = "TINYINT (4) DEFAULT 0 COMMENT '总人数'")
    private Integer sumNumber;
}