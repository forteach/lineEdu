package com.project.course.domain.record;

import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
    @Column(name = "record_id", columnDefinition = "VARCHAR(32) COMMENT '上课记录id'")
    private String recordId;

    @Column(name = "student_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'")
    private String studentId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "sum_time", columnDefinition = "VARCHAR(32) COMMENT '上课总时间(秒)'")
    private long sumTime;

    @Column(name = "grade", columnDefinition = "VARCHAR(32) COMMENT '评分'")
    private String grade;
}
