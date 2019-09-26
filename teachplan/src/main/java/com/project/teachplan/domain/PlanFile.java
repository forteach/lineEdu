package com.project.teachplan.domain;

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
 * 培训班级资料管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "plan_file", comment = "在线计划资料详情表")
@Table(name = "plan_file", indexes = {
        @Index(columnList = "file_id", name = "file_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PlanFile extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
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