package com.project.teachplan.domain.online;

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
public class TeachPlanClassPk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "class_id", columnDefinition = "VARCHAR(40) COMMENT '班级id编号'", insertable = false, updatable = false)
    private String classId;

    @Column(name = "plan_id", columnDefinition = "VARCHAR(40) COMMENT '计划编号'", insertable = false, updatable = false)
    private String planId;
}
