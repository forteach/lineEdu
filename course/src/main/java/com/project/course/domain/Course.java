package com.project.course.domain;


import cn.hutool.core.util.StrUtil;
import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @Description: 科目
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 16:42
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "course", comment = "科目")
@Table(name = "course", indexes = {@Index(columnList = "course_id", name = "course_id_index")})
@EqualsAndHashCode(callSuper = true)
public class Course extends Entitys {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "course_name", columnDefinition = "VARCHAR(40) COMMENT '科目名称'")
    private String courseName;

    @Column(name = "course_number", columnDefinition = "VARCHAR(32) COMMENT '课程字典对应的id'")
    private String courseNumber;

    @Column(name = "alias", columnDefinition = "VARCHAR(32) COMMENT '别名'")
    private String alias;

    @Column(name = "top_pic_src", columnDefinition = "VARCHAR(255) COMMENT'封面图片路径'")
    private String topPicSrc;

    @Column(name = "course_describe", columnDefinition = "MEDIUMTEXT COMMENT'课程描述'")
    private String courseDescribe;

    @Column(name = "learning_time", columnDefinition = "VARCHAR(32) COMMENT '需要学习的总时长(小时)'")
    private String learningTime;

    @Column(name = "video_time_num", columnDefinition = "INT(11) DEFAULT 0 COMMENT '当前课程视频总长度课程视频总长度'", nullable = false)
    private Integer videoTimeNum = this.videoTimeNum == null ? 0 : this.videoTimeNum;

    @Column(name = "video_percentage", columnDefinition = "VARCHAR(32) COMMENT '观看视频占百分比'")
    private String videoPercentage;

    @Column(name = "jobs_percentage", columnDefinition = "VARCHAR(32) COMMENT '平时作业占百分比'")
    private String jobsPercentage;

    @Column(name = "course_type", columnDefinition = "TINYINT(3) DEFAULT 1 COMMENT '课程类型 1 线上 2，线下 3 混合'")
    private int courseType;

    @Column(name = "publish", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N' COMMENT '是否发布 Y/N'")
    public String publish = StrUtil.isBlank(this.publish) ? "N" : this.publish;
}