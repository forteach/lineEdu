package com.project.teachplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mysql.domain.Entitys;
import com.project.teachplan.domain.pk.TeachPlanFileListPk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 11:39
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teach_plan_file_list", comment = "教学计划资料集合表")
@Table(name = "teach_plan_file_list", indexes = {
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index"),
        @Index(columnList = "create_date", name = "create_date_index"),
        @Index(columnList = "class_id", name = "class_id_index"),
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TeachPlanFileListPk.class)
public class TeachPlanFileList extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private TeachPlanFileListPk teachPlanFileListPk;

    private String courseId;

    private String planId;

    private String createDate;

    private String classId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(32) COMMENT '课程字典名字'")
    private String courseName;
}
