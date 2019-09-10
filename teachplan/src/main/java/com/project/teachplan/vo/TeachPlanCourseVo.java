package com.project.teachplan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 17:44
 * @Version: 1.0
 * @Description:
 */
@Data
public class TeachPlanCourseVo implements Serializable {

    /**
     * 课程id
     */
    private String courseId;
    /**
     * 学分
     */
    private String credit;
    /**
     * 线上占比
     */
    private Integer onLinePercentage;
    /**
     * 线下占比
     */
    private String linePercentage;
    /**
    * 教师id
    */
    private String teacherId;
    /** 教师名称*/
    private String teacherName;
}
