package com.project.teachplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.teachplan.domain.base.BaseTeachPlanClass;
import com.project.teachplan.domain.pk.TeachPlanClassPk;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 培训项目班级管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "teach_plan_class", indexes = {
        @Index(columnList = "class_id", name = "class_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@org.hibernate.annotations.Table(appliesTo = "teach_plan_class", comment = "在线教学计划班级管理")
@EqualsAndHashCode(callSuper = true)
@IdClass(TeachPlanClassPk.class)
public class TeachPlanClass extends BaseTeachPlanClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private TeachPlanClassPk teachPlanClassPk;


    public TeachPlanClass() {
    }

    public TeachPlanClass(String classId, String planId, String className, String planName, int classNumber, String centerAreaId, String userId) {
        super(classId, planId, className, planName, classNumber, centerAreaId, userId);
    }
}