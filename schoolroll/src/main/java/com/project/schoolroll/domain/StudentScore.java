package com.project.schoolroll.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "student_score")
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
    @Column(name = "score_id", columnDefinition = "VARCHAR(32) COMMENT '成绩编号'")
    private String scoreId;
    /**
     * 学生id
     */
    @Column(name = "stu_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'")
    private String stuId;
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
    private float courseScore;
}
