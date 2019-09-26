package com.project.teachplan.domain.base;

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
 * @date: 19-9-26 17:52
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BasePlanFile extends Entitys {
    @Id
    @Column(name = "file_id", columnDefinition = "VARCHAR(40) COMMENT '资料编号'")
    private String fileId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(60) COMMENT '资料名称'")
    private String fileName;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '资料URL'")
    private String fileUrl;

    @Column(name = "class_id", columnDefinition = "VARCHAR(60) COMMENT '班级编号'")
    private String classId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '课程id'")
    private String courseId;

    @Column(name = "file_type", columnDefinition = "VARCHAR(32) COMMENT '文件类型'")
    private String fileType;

    @Column(name = "plan_id", columnDefinition = "VARCHAR(40) COMMENT '在线项目计划编号'")
    private String planId;

    @Column(name = "create_date", columnDefinition = "VARCHAR(32) COMMENT '上课日期'")
    private String createDate;
}
