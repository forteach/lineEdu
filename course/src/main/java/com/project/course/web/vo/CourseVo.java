package com.project.course.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/10/13 21:16
 * @Version: 1.0
 * @Description:
 */
@Data
public class CourseVo implements Serializable {
    public String courseId;
    public String courseName;
    public String courseNumber;
    public String alias;
    public String topPicSrc;
    public String courseDescribe;
    public String learningTime;
    public String videoPercentage;
    public String jobsPercentage;
    public String createUser;
    public String createUserName;
    public String chapterId;
    public String chapterName;

    public CourseVo() {
    }

    public CourseVo(String courseId, String courseName, String courseNumber, String alias, String topPicSrc, String courseDescribe,
                    String learningTime, String videoPercentage, String jobsPercentage, String createUser, String createUserName, String chapterId, String chapterName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.alias = alias;
        this.topPicSrc = topPicSrc;
        this.courseDescribe = courseDescribe;
        this.learningTime = learningTime;
        this.videoPercentage = videoPercentage;
        this.jobsPercentage = jobsPercentage;
        this.createUser = createUser;
        this.createUserName = createUserName;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
    }
}