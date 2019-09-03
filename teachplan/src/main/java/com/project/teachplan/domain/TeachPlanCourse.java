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
 * 教学计划课程
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teach_plan_course", comment = "教学计划课程")
@Table(name = "teach_plan_course", indexes = {@Index(columnList = "plan_course_id", name = "plan_course_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeachPlanCourse extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "plan_course_id", columnDefinition = "VARCHAR(32) COMMENT '教学计划编号'")
    private String planCourseId;

    @Column(name = "plan_id", columnDefinition = "VARCHAR(32) COMMENT '计划编号'")
    private String planId;


    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '课程编号'")
    private String courseId;

    public TeachPlanCourse(String planCourseId, String planId, String courseId, String centerId) {
        this.planId = planId;
        this.planCourseId = planCourseId;
        this.courseId = courseId;
        super.centerAreaId=centerId;
    }
}
