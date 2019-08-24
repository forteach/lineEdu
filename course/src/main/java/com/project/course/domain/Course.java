package com.project.course.domain;


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

    @Column(name = "alias", columnDefinition = "VARCHAR(32) COMMENT '别名'")
    private String alias;

    @Column(name = "top_pic_src", columnDefinition = "VARCHAR(255) COMMENT'封面图片路径'")
    private String topPicSrc;

    @Column(name = "course_describe", columnDefinition = "MEDIUMTEXT COMMENT'课程描述'")
    private String courseDescribe;

    @Column(name = "average_score", columnDefinition = "VARCHAR(32) DEFAULT 0 COMMENT '课程平均分数'")
    private String averageScore;

    @Column(name = "review_amount", columnDefinition = "INT(11) DEFAULT 0 COMMENT '评价数量'")
    private Integer reviewAmount;

    // 新添加属性

    @Column(name = "course_type", columnDefinition = "VARCHAR(32) COMMENT '课程类别 公共基础课,实训课,专业'")
    private String courseType;

    @Column(name = "is_required", columnDefinition = "VARCHAR(32) COMMENT '是否必修课 Y/N'")
    private String isRequired;

    @Column(name = "scoring_method", columnDefinition = "VARCHAR(32) COMMENT '评分方式 笔试,口试,网上考试'")
    private String scoringMethod;

    @Column(name = "learning_time", columnDefinition = "VARCHAR(32) COMMENT '需要学习的总时长(小时)'")
    private String learningTime;

    @Column(name = "credit", columnDefinition = "VARCHAR(32) COMMENT '学分'")
    private String credit;

    @Column(name = "video_percentage", columnDefinition = "VARCHAR(32) COMMENT '观看视频占百分比'")
    private String videoPercentage;

    @Column(name = "jobs_percentage", columnDefinition = "VARCHAR(32) COMMENT '平时作业占百分比'")
    private String jobsPercentage;
}