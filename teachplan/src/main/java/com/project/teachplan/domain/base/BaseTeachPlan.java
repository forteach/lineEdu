package com.project.teachplan.domain.base;

import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 17:57
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public abstract class BaseTeachPlan extends Entitys {
    @Id
    @Column(name = "plan_id", columnDefinition = "VARCHAR(32) COMMENT '计划编号'")
    private String planId;

    @Column(name = "plan_name", columnDefinition = "VARCHAR(60) COMMENT '计划名称'")
    private String planName;

    @Column(name = "start_date", columnDefinition = "VARCHAR(60) COMMENT '计划开始执行时间'")
    private String startDate;

    @Column(name = "end_date", columnDefinition = "VARCHAR(60) COMMENT '计划执行结束时间'")
    private String endDate;

    @Column(name = "plan_admin", columnDefinition = "VARCHAR(60) COMMENT '计划负责人'")
    private String planAdmin;

    @Column(name = "course_number", columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '计划的课程数量'")
    private Integer courseNumber;

    @Column(name = "class_number", columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '计划的班级数'")
    private Integer classNumber;

    @Column(name = "sum_number", columnDefinition = "TINYINT (4) DEFAULT 0 COMMENT '总人数'")
    private Integer sumNumber;
}
