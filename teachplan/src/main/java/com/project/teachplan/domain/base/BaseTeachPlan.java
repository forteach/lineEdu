package com.project.teachplan.domain.base;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    @Column(name = "course_number", nullable = false, columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '计划的课程数量'")
    private Integer courseNumber = this.courseNumber == null ? 0 : this.courseNumber;

    @Column(name = "class_id", columnDefinition = "VARCHAR(32) COMMENT '班级id'")
    private String classId;

    @Column(name = "class_name", columnDefinition = "VARCHAR(32) COMMENT '班级名称'")
    private String className;
//    @Column(name = "class_number", nullable = false, columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '计划的班级数'")
//    private Integer classNumber = this.classNumber == null ? 0 : this.classNumber;

//    @Column(name = "sum_number", nullable = false, columnDefinition = "TINYINT (4) DEFAULT 0 COMMENT '总人数'")
//    private Integer sumNumber = this.sumNumber == null ? 0 : this.sumNumber;

    /** 专业名称*/
    @Column(name = "specialty_name", columnDefinition = "VARCHAR(32) COMMENT '专业名称'")
    private String specialtyName;

    @Column(name = "grade", columnDefinition = "VARCHAR(32) COMMENT '年级'")
    private String grade;

    @Column(name = "center_name", columnDefinition = "VARCHAR(64) COMMENT '学习中心名称'")
    private String centerName;
}
