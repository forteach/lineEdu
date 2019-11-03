package com.project.course.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.course.domain.pk.CourseStudyPk;
import com.project.mysql.domain.Entitys;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-27 14:37
 * @version: 1.0
 * @description: 学生学习情况表
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_study", indexes = {
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "student_id", name = "student_id_index")
})
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "course_study", comment = "科目课程学习情况")
@DynamicInsert
@DynamicUpdate
@IdClass(CourseStudyPk.class)
public class CourseStudy extends Entitys implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private CourseStudyPk courseStudyPk;

    private String courseId;

    private String studentId;

    @Column(name = "on_line_time", columnDefinition = "INT(11) DEFAULT 0 COMMENT '在线学习时长(秒)'", nullable = false)
    private Integer onLineTime;

    @Column(name = "on_line_time_sum", columnDefinition = "INT(11) DEFAULT 0 COMMENT '课程视频总长度(秒)'", nullable = false)
    private Integer onLineTimeSum;

    @Column(name = "answer_sum", columnDefinition = "INT(11) DEFAULT 0 COMMENT '回答题目数量'", nullable = false)
    private Integer answerSum;

    @Column(name = "correct_sum", columnDefinition = "INT(11) DEFAULT 0 COMMENT '正确题目总数量'", nullable = false)
    private Integer correctSum;

//    @Column(name = "chapter_id", columnDefinition = "VARCHAR(32) COMMENT '最近学习到的章节位置'")
//    private String chapterId;

    @Column(name = "study_status", columnDefinition = "INT(2) DEFAULT 0 COMMENT '学习状态 0 未学习　1 在学习　2 已完结'")
    private Integer studyStatus;

    @Column(name = "semester_grade", columnDefinition = "VARCHAR(32) COMMENT '学期成绩/平时成绩'")
    private String semesterGrade;

    @Column(name = "exam_grade", columnDefinition = "VARCHAR(32) COMMENT '考试成绩'")
    private String examGrade;

    @Column(name = "exam_results", nullable = false, columnDefinition = "INT(2) DEFAULT 1 COMMENT '考试结果状态 0 已经通过 1 未通过'")
    private Integer examResults;

    @Column(name = "make_up_examination", columnDefinition = "INT(2) COMMENT '是否需要补考 0 需要补考 1 不需要补考'")
    private Integer makeUpExamination = this.makeUpExamination == null ? 0 : this.makeUpExamination;
}