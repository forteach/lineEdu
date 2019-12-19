package com.project.schoolroll.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:20
 * @version: 1.0
 * @description:　学生成绩
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "student_score", indexes = {
        @Index(columnList = "score_id", name = "score_id_index"),
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "student_id", name = "student_id_index")
})
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "student_score", comment = "学生成绩")
@AllArgsConstructor
@NoArgsConstructor
public class StudentScore extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 成绩编号
     */
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "score_id", columnDefinition = "VARCHAR(32) COMMENT '成绩编号'")
    private String scoreId;
    /**
     * 学生id
     */
    @Column(name = "student_id", nullable = false, columnDefinition = "VARCHAR(32) COMMENT '学生id'")
    private String studentId;
    /**
     * 课程id
     */
    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '课程id'")
    private String courseId;
    /**
     * 课程类别, bx 必修, xx 选修, sj 实践
     */
    @Column(name = "course_type", columnDefinition = "VARCHAR(32) COMMENT '课程类别, bx 必修, xx 选修, sj 实践'")
    private String courseType;
    /**
     * 课程分数
     */
    @Column(name = "course_score", columnDefinition = "FLOAT(5,2) COMMENT '课程分数'")
    private Float courseScore;
    /**
     * 学期
     */
    @Column(name = "term", columnDefinition = "VARCHAR(32) COMMENT '学期'")
    private String term;
    /**
     * 学年
     */
    @Column(name = "school_year", columnDefinition = "VARCHAR(32) COMMENT '学年'")
    private String schoolYear;

    @Column(name = "on_line_score", columnDefinition = "VARCHAR(32) COMMENT '线上成绩'")
    private String onLineScore;

    @Column(name = "off_line_score", columnDefinition = "VARCHAR(32) COMMENT '线下成绩'")
    private String offLineScore;

    @Column(name = "on_line_percentage", columnDefinition = "TINYINT(4) COMMENT '线上占比'")
    private Integer onLinePercentage;
    /**
     * 线下占比
     */
    @Column(name = "line_percentage", columnDefinition = "TINYINT(4) COMMENT '线下占比'")
    private Integer linePercentage;

    @Column(name = "type", columnDefinition = "VARCHAR(32) COMMENT '课程类型　1.线上，2.线下,3.混合'")
    private String type;
}