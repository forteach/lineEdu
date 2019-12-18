package com.project.teachplan.vo;

import lombok.Data;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 12:13
 * @version: 1.0
 * @description:
 */
@Data
public class CourseTeacherVo {
    private String courseId;
//    private String teacherId;
    private Integer status;
    private Integer countStatus;

    public CourseTeacherVo(String courseId,
//                           String teacherId,
                           Integer status, Integer countStatus) {
        this.courseId = courseId;
//        this.teacherId = teacherId;
        this.status = status;
        this.countStatus = countStatus;
    }
}
