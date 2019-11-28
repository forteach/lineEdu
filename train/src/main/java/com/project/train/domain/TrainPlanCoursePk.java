package com.project.train.domain;

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
public class TrainPlanCoursePk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "course_id", columnDefinition = "VARCHAR(40) COMMENT '培训课程编号'", insertable = false, updatable = false)
    private String courseId;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'", insertable = false, updatable = false)
    private String pjPlanId;
}
