package com.project.course.web.vo;

import lombok.AllArgsConstructor;
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
    private String teacherId;
    private Integer status;
    private Integer countStatus;

    public CourseTeacherVo(String courseId, String teacherId, Integer status, Integer countStatus) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.status = status;
        this.countStatus = countStatus;
    }
}
