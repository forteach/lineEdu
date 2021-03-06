package com.project.course.web.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/10/13 21:34
 * @Version: 1.0
 * @Description:
 */
@Data
@NoArgsConstructor
public class CourseTeacherVo implements Serializable {
    private String courseId;
    private Integer status;
    private Integer countStatus;

//    private String teacherId;

    public CourseTeacherVo(String courseId,
//                           String teacherId,
                           Integer status, Integer countStatus) {
        this.courseId = courseId;
        this.status = status;
        this.countStatus = countStatus;
//        this.teacherId = teacherId;
    }
}
