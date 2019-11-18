package com.project.teachplan.domain;

import com.project.teachplan.domain.base.BaseTeachPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
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
@NoArgsConstructor
@AllArgsConstructor
public class TeachPlan extends BaseTeachPlan implements Serializable {

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) DEFAULT 1 COMMENT '计划完成状态 0 完成 1 未完成/进行中'")
    private Integer status;

    @Column(name = "count_status", nullable = false, columnDefinition = "TINYINT(2) DEFAULT 1 COMMENT '统计计算完成状态 0 完成 1 未完成/进行中'")
    private Integer countStatus;
}