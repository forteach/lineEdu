package com.project.teachplan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 记录教学日志列表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
class TeachPlanFileListPk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "course_id", columnDefinition = "VARCHAR(40) COMMENT '课程id编号'", insertable = false, updatable = false)
    private String courseId;

    @Column(name = "plan_id", columnDefinition = "VARCHAR(40) COMMENT '计划编号'", insertable = false, updatable = false)
    private String planId;

    @Column(name = "create_date", columnDefinition = "VARCHAR(32) COMMENT '创建日期'", insertable = false, updatable = false)
    private String createDate;

    @Column(name = "class_id", columnDefinition = "VARCHAR(32) COMMENT '班级Id'", insertable = false, updatable = false)
    private String classId;
}