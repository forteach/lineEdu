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
public class TrainPlanClassPk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "tran_class_id", columnDefinition = "VARCHAR(40) COMMENT '培训班级编号'")
    private String tranClassId;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    private String pjPlanId;
}
