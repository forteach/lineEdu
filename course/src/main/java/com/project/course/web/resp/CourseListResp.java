package com.project.course.web.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
public class CourseListResp implements Serializable {

    private String courseId;

    private String alias;

    private String courseName;

    private String courseNumber;

    private String lessonPreparationType;

    private String topPicSrc;

    private String courseDescribe;

    private String chapterId;

    private String chapterName;

    private String joinChapterId;

    private String joinChapterName;

    private String teacherId;

    private String teacherName;

    public CourseListResp(String courseId, String courseName, String courseNumber, String lessonPreparationType, String topPicSrc, String alias) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.lessonPreparationType = lessonPreparationType;
        this.topPicSrc = topPicSrc;
        this.alias = alias;
    }

    public CourseListResp() {
    }
}
