package com.project.teachplan.domain.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class TeachPlanCoursePk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "course_id", columnDefinition = "VARCHAR(40) COMMENT '课程id编号'", insertable = false, updatable = false)
    private String courseId;

    @Column(name = "plan_id", columnDefinition = "VARCHAR(40) COMMENT '计划编号'", insertable = false, updatable = false)
    private String planId;
}
