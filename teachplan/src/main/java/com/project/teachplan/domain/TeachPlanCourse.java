package com.project.teachplan.domain;


import com.project.mysql.domain.Entitys;
import com.project.teachplan.domain.online.TeachPlanClassPk;
import com.project.teachplan.domain.online.TeachPlanCoursePk;
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
@Table(name = "teach_plan_course", indexes = {
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TeachPlanCoursePk.class)
public class TeachPlanCourse extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TeachPlanCoursePk teachPlanCoursePk;

    private String planId;

    private String courseId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(32) COMMENT '课程名称'")
    private String courseName;

    public TeachPlanCourse(String planId, String courseId, String courseName) {
        this.planId = planId;
        this.courseId = courseId;
        this.courseName = courseName;
    }
}
