package com.project.course.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2019/12/27 11:59
 * @version: 1.0
 * @description:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAllStudyVo {
    /**习题百分比*/
//    public String questionPercentage;

    public int offlineCount;

    /** 视频百分比*/
    public String videoTimePercentage;

    /** 课程总数*/
    public int courseCount;

    public int mixCourseCount;

    public int courseSum;
}