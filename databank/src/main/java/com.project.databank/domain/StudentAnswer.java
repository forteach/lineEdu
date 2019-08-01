package com.project.databank.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:　学生答题的答案
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/7 10:25
 */
@Data
@Entity
@Table(name = "student_answer", indexes = {@Index(columnList = "class_question_id", name = "class_question_id"),
        @Index(columnList = "txt_id", name = "txt_id_index")})
@IdClass(StudentAnswerFundPrimarykey.class)
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "student_answer", comment = "学生答题的答案")
@AllArgsConstructor
@NoArgsConstructor
public class StudentAnswer extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StudentAnswerFundPrimarykey studentAnswerFundPrimarykey;

    private String classQuestionId;

    private String examineeId;

    @Column(name = "txt_id", columnDefinition = "varchar(32) COMMENT '考题内容'")
    private String txtId;

    @Column(name = "reply_content", columnDefinition = "varchar(5000) COMMENT '回答内容'")
    private String replyContent;

    @Column(name = "correct_answer", columnDefinition = "varchar(5000) COMMENT '正确答案'")
    private String correctAnswer;

    @Column(name = "way_participation", columnDefinition = "varchar(32) COMMENT '参与方式'")
    private String wayParticipation;

    @Column(name = "answer_results", columnDefinition = "bit COMMENT '答题结果'")
    private boolean answerResults;

    @Column(name = "teacher_id", columnDefinition = "varchar(32) COMMENT '批改教师'")
    private String teacherId;
}