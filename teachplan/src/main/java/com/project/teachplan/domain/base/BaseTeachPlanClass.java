package com.project.teachplan.domain.base;

import com.project.mysql.domain.Entitys;
import com.project.teachplan.domain.pk.TeachPlanClassPk;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 11:07
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTeachPlanClass extends Entitys {
    private static final long serialVersionUID = 1L;

    private String classId;

    private String planId;

    @Column(name = "class_name", columnDefinition = "VARCHAR(60) COMMENT '班级名称'")
    private String className;

    @Column(name = "plan_name", columnDefinition = "VARCHAR(100) COMMENT '计划名称'")
    private String planName;

    @Column(name = "classNumber", columnDefinition = "TINYINT (3) DEFAULT 0 COMMENT '班级人数'")
    private Integer classNumber;

    public BaseTeachPlanClass(String classId, String planId, String className, String planName, int classNumber, String centerAreaId, String userId) {
        super(userId, userId, centerAreaId);
        this.classId = classId;
        this.planId = planId;
        this.className = className;
        this.planName = planName;
        this.classNumber = classNumber;
    }
}