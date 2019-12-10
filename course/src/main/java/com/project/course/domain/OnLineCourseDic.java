package com.project.course.domain;

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
 * 培训项目课程字典管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "on_line_course_dic", comment = "在线课程字典管理")
@Table(name = "on_line_course_dic", indexes = {@Index(columnList = "course_id", name = "course_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OnLineCourseDic extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "course_id", columnDefinition = "VARCHAR(40) COMMENT '课程编号'")
    private String courseId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(40) COMMENT '课程名称'")
    private String courseName;

    @Column(name = "type", columnDefinition = "VARCHAR(32) COMMENT '课程类型　线上，线下'")
    private String type;
}
