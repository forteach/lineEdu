package com.project.course.web.vo;

import lombok.Getter;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2020/1/9 14:23
 * @Version: 1.0
 * @Description:
 */
@Getter
public class QuestionVo implements Serializable {
    private String courseId;
    private String courseName;
    private String chapterId;
    private String chapterName;
    private String teacherId;
    private String teacherName;
    private String centerAreaId;
    private String centerName;

    public QuestionVo() {
    }

    public QuestionVo(String courseId, String courseName, String chapterId,
                      String chapterName, String teacherId,
                      String teacherName, String centerAreaId, String centerName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.centerAreaId = centerAreaId;
        this.centerName = centerName;
    }
}
