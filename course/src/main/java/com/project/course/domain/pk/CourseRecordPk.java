package com.project.course.domain.pk;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Data
@Embeddable
public class CourseRecordPk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'", updatable = false, insertable = false)
    private String studentId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'", updatable = false, insertable = false)
    private String courseId;

    @Column(name = "chapter_id", columnDefinition = "VARCHAR(32) COMMENT '章节id'", updatable = false, insertable = false)
    private String chapterId;
}