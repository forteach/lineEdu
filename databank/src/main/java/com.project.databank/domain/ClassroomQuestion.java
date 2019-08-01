package com.project.databank.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description: 课堂问题
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/7 10:06
 */
@Data
@Entity
@Table(name = "classroom_question", indexes = {@Index(columnList = "teacher_id", name = "teacher_id_index"),
        @Index(columnList = "course_id", name = "course_id_index")})
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "classroom_question", comment = "课堂问题")
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomQuestion extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "class_question_id", columnDefinition = "VARCHAR(32) COMMENT '问题册编号'")
    private String classQuestionId;

    @Column(name = "teacher_id", columnDefinition = "VARCHAR(32) COMMENT '创建教师'")
    private String teacherId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "class_question_name", columnDefinition = "VARCHAR(255) COMMENT '问题册名称'")
    private String classQuestionName;

    @Column(name = "class_question_type", columnDefinition = "INT(11) COMMENT '问题册类型'")
    private Integer classQuestionType;


}
