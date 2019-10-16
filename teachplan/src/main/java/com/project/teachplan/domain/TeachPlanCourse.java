package com.project.teachplan.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.teachplan.domain.base.BaseTeachPlanCourse;
import com.project.teachplan.domain.pk.TeachPlanCoursePk;
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
@NoArgsConstructor
@IdClass(TeachPlanCoursePk.class)
public class TeachPlanCourse extends BaseTeachPlanCourse implements Serializable  {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private TeachPlanCoursePk teachPlanCoursePk;

    public TeachPlanCourse(String planId, String courseId, String courseName, String credit, Integer onLinePercentage,
                           Integer linePercentage, String teacherId, String teacherName, String centerAreaId, String userId) {
        super(planId, courseId, courseName, credit, onLinePercentage, linePercentage, teacherId, teacherName, centerAreaId, userId);
    }
}
