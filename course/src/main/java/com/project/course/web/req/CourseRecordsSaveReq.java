package com.project.course.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 10:55
 * @version: 1.0
 * @description:
 */
@Data
public class CourseRecordsSaveReq implements Serializable {
    /** 学生id*/
    private String studentId;
    /** 课程id*/
    private String courseId;
    /** 章节id*/
    private String chapterId;
    /** 观看视频位置*/
    private String locationTime;
    /** 观看视频时间长度*/
    private int duration;
    /** 视频总长度*/
    private int videoDuration;
    /** 学习中心*/
    private String centerAreaId;

    private String createUser;

    private String courseName;

    public CourseRecordsSaveReq() {
    }

    public CourseRecordsSaveReq(String studentId, String courseId, String chapterId, String centerAreaId, String createUser, String courseName) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.chapterId = chapterId;
        this.centerAreaId = centerAreaId;
        this.createUser = createUser;
        this.courseName = courseName;
    }
}
