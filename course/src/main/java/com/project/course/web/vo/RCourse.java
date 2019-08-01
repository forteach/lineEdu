package com.project.course.web.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 科目
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 16:42
 */
@Data
public class RCourse implements Serializable {

    private static final long serialVersionUID = 1L;


    private String courseId;


    private String courseName;

    private String courseNumber;

    /**
     * 授课类型是多选
     */
    private String teachingType;


    private String lessonPreparationType;


    private String topPicSrc;

    private String courseDescribe;

    private String alias;

}