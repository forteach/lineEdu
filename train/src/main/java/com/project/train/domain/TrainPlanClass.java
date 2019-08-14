package com.project.train.domain;

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
 * 培训计划课程管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_plan_class", comment = "培训计划课程管理")
@Table(name = "train_plan_class")
@IdClass(TrainPlanClassPk.class)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainPlanClass extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TrainPlanClassPk trainPlanClassPk;

    private String tranClassId;

    private String pjPlanId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(40) COMMENT '培训课程名称'")
    private String courseName;

    @Column(name = "teacher_name", columnDefinition = "VARCHAR(60) COMMENT '培训课程教师名称'")
    private String teacherName;

    @Column(name = "credit", columnDefinition = "VARCHAR(10) COMMENT '培训课程学分'")
    private String credit;

    public TrainPlanClass(String tranClassId, String pjPlanId, String courseName, String teacherName, String credit, String centerId) {
        this.tranClassId = tranClassId;
        this.pjPlanId = pjPlanId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.credit = credit;
        super.centerAreaId=centerId;
    }
}