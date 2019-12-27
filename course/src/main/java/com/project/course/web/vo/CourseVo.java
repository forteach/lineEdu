package com.project.course.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/10/13 21:16
 * @Version: 1.0
 * @Description:
 */
@Data
@NoArgsConstructor
public class CourseVo implements Serializable {
    private String courseId;
    private String courseName;
    private String courseNumber;
    private Integer courseType;
    private String alias;
    private String topPicSrc;
    private String courseDescribe;
    private String learningTime;
    private String videoPercentage;
    private String jobsPercentage;

//    public String createUser;
//    public String createUserName;

    private String chapterId;
    private String chapterName;
    /**是否学完 0完成 1 进行中*/
    private String finish;

    public CourseVo(String courseId, String courseName, String courseNumber, Integer courseType, String alias, String topPicSrc, String courseDescribe,
                    String learningTime, String videoPercentage, String jobsPercentage,
//                    String createUser, String createUserName,
                    String chapterId, String chapterName, String finish) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.courseType = courseType;
        this.alias = alias;
        this.topPicSrc = topPicSrc;
        this.courseDescribe = courseDescribe;
        this.learningTime = learningTime;
        this.videoPercentage = videoPercentage;
        this.jobsPercentage = jobsPercentage;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.finish = finish;
//        this.createUser = createUser;
//        this.createUserName = createUserName;
    }
}