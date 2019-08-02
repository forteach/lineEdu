//package com.project.course.domain;
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
// * @Date: 18-11-16 15:36
// * @Version: 1.0
// * @Description: 课程分享范围
// */
//@Data
//@Entity
//@Builder
//@Table(name = "course_share", indexes = {@Index(columnList = "share_id", name = "share_id_index"),
//        @Index(columnList = "chapter_id", name = "chapter_id_index"), @Index(columnList = "course_id", name = "course_id_index")})
//@EqualsAndHashCode(callSuper = true)
//@org.hibernate.annotations.Table(appliesTo = "course_share", comment = "分享范围")
//@AllArgsConstructor
//@NoArgsConstructor
//public class CourseShare extends Entitys {
//
//    @Id
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GeneratedValue(generator = "system-uuid")
//    @Column(name = "share_id", columnDefinition = "VARCHAR(32) COMMENT '分享编号'")
//    private String shareId;
//
//    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
//    private String courseId;
//
//    @Column(name = "chapter_id", columnDefinition = "CHAR(32) COMMENT '章节编号'")
//    private String chapterId;
//
//    @Column(name = "share_area", columnDefinition = "INT COMMENT '分享范围 1,全部　２,章节'")
//    private String shareArea;
//
//}
