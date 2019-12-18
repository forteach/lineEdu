package com.project.teachplan.domain.base;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 10:04
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTeachPlanCourse extends Entitys {

    private String planId;

    private String courseId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(32) COMMENT '课程名称'")
    private String courseName;

    /**
     * 学分
     */
    @Column(name = "credit", columnDefinition = "VARCHAR(32) COMMENT '学分'")
    private String credit;
    /**
     * 线上占比
     */
    @Column(name = "on_line_percentage", columnDefinition = "TINYINT(4) COMMENT '线上占比'")
    private Integer onLinePercentage;
    /**
     * 线下占比
     */
    @Column(name = "line_percentage", columnDefinition = "TINYINT(4) COMMENT '线下占比'")
    private Integer linePercentage;

    @Column(name = "type", columnDefinition = "VARCHAR(32) COMMENT '课程类型　1.线上，2.线下,3.混合'")
    private String type;

//    @Column(name = "teacher_Id", columnDefinition = "VARCHAR(32) COMMENT '创建教师id'")
//    private String teacherId;

//    @Column(name = "teacherName", columnDefinition = "VARCHAR(32) COMMENT '教师名称'")
//    private String teacherName;

    public BaseTeachPlanCourse(String planId, String courseId, String courseName, String credit, Integer onLinePercentage,
                               Integer linePercentage,
//                               String teacherId, String teacherName,
                               String centerAreaId, String userId, String type) {
        super(userId, userId, centerAreaId);
        this.planId = planId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.onLinePercentage = onLinePercentage;
        this.linePercentage = linePercentage;
        this.type = type;
//        this.teacherId = teacherId;
//        this.teacherName = teacherName;
    }
}