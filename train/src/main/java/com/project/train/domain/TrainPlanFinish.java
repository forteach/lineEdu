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
 * 培训计划资料填写情况
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "traint_plan_finish", comment = "培训计划资料填写情况")
@Table(name = "traint_plan_finish", indexes = {@Index(columnList = "pj_plan_id", name = "pj_plan_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainPlanFinish extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    private String pjPlanId;

    @Column(name = "train_project_name", columnDefinition = "VARCHAR(60) COMMENT '培训项目名称'")
    private String trainProjectName;

    @Column(name = "is_course", columnDefinition = "tinyint(2) COMMENT '课程是否添加'")
    private int isCourse;

    @Column(name = "is_class", columnDefinition = "tinyint(2) COMMENT '班级是否添加'")
    private int isClass;

    @Column(name = "is_student", columnDefinition = "tinyint(2) COMMENT '班级成员是否添加'")
    private int isStudent;

    @Column(name = "is_file", columnDefinition = "tinyint(2) COMMENT '签名表是否添加'")
    private int isFile;

    @Column(name = "is_all", columnDefinition = "tinyint(2) COMMENT '是否全部添加'")
    private int isAll;


}
