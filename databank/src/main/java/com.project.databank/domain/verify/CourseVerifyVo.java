package com.project.databank.domain.verify;

import cn.hutool.core.util.StrUtil;
import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:16
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@Table(name = "course_verify_vo", indexes = {
        @Index(columnList = "vo_id", name = "vo_id_index"),
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "course_name", name = "course_name_index"),
        @Index(columnList = "verify_status", name = "verify_status_index")
})
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Table(appliesTo = "course_verify_vo", comment = "课程修改记录")
public class CourseVerifyVo extends Entitys implements Serializable {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "vo_id", columnDefinition = "VARCHAR(32) COMMENT '修改的课程id'")
    private String voId;

    @Column(name = "submit_type", columnDefinition = "VARCHAR(32) COMMENT '提交审核的类型'")
    private String submitType;

    @Column(name = "course_name", columnDefinition = "VARCHAR(40) COMMENT '科目名称'")
    private String courseName;

    @Column(name = "teacher_id", columnDefinition = "VARCHAR(32) COMMENT '教师id'")
    private String teacherId;

    @Column(name = "teacher_name", columnDefinition = "VARCHAR(32) COMMENT '教师名称'")
    private String teacherName;

    @Column(name = "centerName", columnDefinition = "VARCHAR(64) COMMENT '学习中心名称'")
    private String centerName;

    @Column(name = "file_id", columnDefinition = "VARCHAR(32) COMMENT '文件编号'")
    public String fileId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    public String courseId;

    @Column(name = "chapter_id", columnDefinition = "VARCHAR(32) COMMENT '章节编号'")
    public String chapterId;

    @Column(name = "file_name", columnDefinition = "TEXT COMMENT '文件名称'")
    public String fileName;

    @Column(name = "file_type", columnDefinition = "VARCHAR(10) COMMENT '文件类型'")
    public String fileType;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '文件URL'")
    public String fileUrl;

    @Column(name = "datum_type", columnDefinition = "VARCHAR(32) COMMENT '资料类型 1文档　2图册　3视频　4音频　5链接'")
    private String datumType;

    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
    private String verifyStatus = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;

    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;

    @Column(name = "course_type", nullable = false, columnDefinition = "CHAR(2) COMMENT '提交的课程类型'")
    private String courseType;

    @Column(name = "question_Id", columnDefinition = "VARCHAR(40) COMMENT '要修改的习题Id' ")
    private String questionId;
}