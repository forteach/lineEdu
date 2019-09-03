package com.project.teachplan.domain;


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
 * 教学计划
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teach_plan", comment = "教学计划")
@Table(name = "teach_plan", indexes = {@Index(name = "plan_id_index", columnList = "plan_id")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeachPlan extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "plan_id", columnDefinition = "VARCHAR(32) COMMENT '计划编号'")
    private String planId;

    @Column(name = "grade_id", columnDefinition = "VARCHAR(32) COMMENT '年级编号'")
    private String gradeId;

    @Column(name = "specialty_ids", columnDefinition = "VARCHAR(32) COMMENT '所属专业'")
    private String specialtyIds;

    public TeachPlan(String planId, String gradeId, String specialtyIds,String centerId) {
        this.planId = planId;
        this.gradeId = gradeId;
        this.specialtyIds = specialtyIds;
        super.centerAreaId=centerId;
    }
}
