package com.project.teachplan.repository.dto;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/11/16 22:05
 * @Version: 1.0
 * @Description:
 */
public interface IPlanCourseDto {
    /**线下占比*/
    public Integer getLinePercentage();
    /** 线上占比*/
    public Integer getOnLinePercentage();
    /** 课程ID*/
    public String getCourseId();
    /** 课程名称*/
    public String getCourseName();
    /** 平时作业占百分比*/
    public String getJobsPercentage();
    /** 观看视频占百分比*/
    public String getVideoPercentage();
}