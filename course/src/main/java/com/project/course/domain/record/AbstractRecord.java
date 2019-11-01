package com.project.course.domain.record;

import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 10:40
 * @version: 1.0
 * @description:
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractRecord extends Entitys {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "record_id", columnDefinition = "VARCHAR(32) COMMENT '上课记录id'")
    private String recordId;

    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'")
    private String studentId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(32) COMMENT '课程名称'")
    private String courseName;

    @Column(name = "chapter_id", columnDefinition = "VARCHAR(32) COMMENT '章节id'")
    private String chapterId;

    @Column(name = "sum_time", columnDefinition = "BIGINT(20) DEFAULT 0 COMMENT '上课总时间(秒)'", nullable = false)
    private Long sumTime = this.sumTime == null ? 0 : this.sumTime;

    @Column(name = "grade", columnDefinition = "VARCHAR(32) COMMENT '评分'")
    private String grade;
}