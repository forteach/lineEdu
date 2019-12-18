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

    private String topPicSrc;

    private String courseDescribe;

    private String alias;
}