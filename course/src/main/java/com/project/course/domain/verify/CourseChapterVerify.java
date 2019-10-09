//package com.project.course.domain.verify;
//
//import com.project.mysql.domain.Entitys;
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
///**
// * @Auther: zhangyy
// * @Email: zhang10092009@hotmail.com
// * @Date: 18-11-16 14:09
// * @Version: 1.0
// * @Description: 课程讲课目录
// */
//@Data
//@Entity
//@Builder
//@EqualsAndHashCode(callSuper = true)
//@Table(name = "course_chapter_verify", indexes = {
//        @Index(columnList = "chapter_id", name = "chapter_id_index"),
//        @Index(columnList = "course_id", name = "course_id_index")
//})
//@org.hibernate.annotations.Table(appliesTo = "course_chapter_verify", comment = "课程章节目录审批")
//@GenericGenerator(name = "system-uuid", strategy = "uuid")
//@AllArgsConstructor
//@NoArgsConstructor
//public class CourseChapterVerify extends Entitys {
//
//    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
//    private String courseId;
//
//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @Column(name = "chapter_id", columnDefinition = "CHAR(32) COMMENT '章节编号'")
//    private String chapterId;
//
//    @Column(name = "chapter_name", columnDefinition = "CHAR(60) COMMENT '章节名称'")
//    private String chapterName;
//
//    @Column(name = "chapter_parent_id", columnDefinition = "CHAR(32) COMMENT '章节父编号'")
//    private String chapterParentId;
//
//    @Column(name = "sort", columnDefinition = "CHAR(3) COMMENT '当前层所处的顺序'")
//    private String sort;
//
//    @Column(name = "chapter_type", columnDefinition = "CHAR(3) COMMENT '目录类型：1.章、２.节、3.小节 '")
//    private String chapterType;
//
//    @Column(name = "chapter_level", columnDefinition = "CHAR(32) COMMENT '层级　树层级'")
//    private String chapterLevel;
//
//    @Column(name = "publish", columnDefinition = "CHAR(1) COMMENT '是否发布 Y/N'")
//    public String publish;
//
//    @Column(name = "random_questions_number", columnDefinition = "INT(3) DEFAULT 0 COMMENT '随机题目数量'")
//    private Integer randomQuestionsNumber;
//
//    @Column(name = "video_time", columnDefinition = "INT(6) DEFAULT 0 COMMENT '需要观看视频长度(秒)'")
//    private Integer videoTime;
//
//    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
//    private String remark;
//
//    @Column(name = "verify_status", columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
//    private String verifyStatus;
//}