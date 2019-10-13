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
@AllArgsConstructor
@NoArgsConstructor
public class CourseTeacherVo implements Serializable {
    public String courseId;
    public String teacherId;
}
