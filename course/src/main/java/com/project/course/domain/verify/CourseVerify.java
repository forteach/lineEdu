//package com.project.course.domain.verify;
//
//
//import cn.hutool.core.util.StrUtil;
//import com.project.mysql.domain.Entitys;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
//import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;
//
///**
// * @Description: 科目
// * @author: liu zhenming
// * @version: V1.0
// * @date: 2018/11/6 16:42
// */
//@Data
//@Entity
//@DynamicUpdate
//@DynamicInsert
//@org.hibernate.annotations.Table(appliesTo = "course_verify", comment = "科目课程审批")
//@Table(name = "course_verify", indexes = {@Index(columnList = "course_id", name = "course_id_index")})
//@EqualsAndHashCode(callSuper = true)
//public class CourseVerify extends Entitys {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
//    private String courseId;
//
//    @Column(name = "course_name", columnDefinition = "VARCHAR(40) COMMENT '科目名称'")
//    private String courseName;
//
//    @Column(name = "course_number", columnDefinition = "VARCHAR(32) COMMENT '课程字典对应的id'")
//    private String courseNumber;
//
//    @Column(name = "alias", columnDefinition = "VARCHAR(32) COMMENT '别名'")
//    private String alias;
//
//    @Column(name = "top_pic_src", columnDefinition = "VARCHAR(255) COMMENT'封面图片路径'")
//    private String topPicSrc;
//
//    @Column(name = "course_describe", columnDefinition = "MEDIUMTEXT COMMENT'课程描述'")
//    private String courseDescribe;
//
//    @Column(name = "learning_time", columnDefinition = "VARCHAR(32) COMMENT '需要学习的总时长(小时)'")
//    private String learningTime;
//
//    @Column(name = "video_percentage", columnDefinition = "VARCHAR(32) COMMENT '观看视频占百分比'")
//    private String videoPercentage;
//
//    @Column(name = "jobs_percentage", columnDefinition = "VARCHAR(32) COMMENT '平时作业占百分比'")
//    private String jobsPercentage;
//
//    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
//    private String remark;
//
//    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
//    private String verifyStatus = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;
//}