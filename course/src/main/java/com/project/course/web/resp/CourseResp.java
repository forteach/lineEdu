package com.project.course.web.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 09:49
 * @Version: 1.0
 * @Description:
 */
@Data
public class CourseResp implements Serializable {

    private String courseId;

    private String courseName;

    private String courseNumber;

    private String lessonPreparationType;

    private String topPicSrc;

    private String shareType;

    private String courseDescribe;

    private String shareId;

    private String alias;

    private String teachingType;

    public CourseResp(String courseId, String courseName, String courseNumber,
                      String lessonPreparationType, String teachingType, String topPicSrc,
                      String shareType, String courseDescribe, String shareId, String alias) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.lessonPreparationType = lessonPreparationType;
        this.teachingType = teachingType;
        this.topPicSrc = topPicSrc;
        this.shareType = shareType;
        this.courseDescribe = courseDescribe;
        this.shareId = shareId;
        this.alias = alias;
    }
}