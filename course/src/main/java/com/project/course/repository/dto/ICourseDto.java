package com.project.course.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-18 14:19
 * @version: 1.0
 * @description:
 */
public interface ICourseDto {

    public String getCourseId();

    public String getCourseName();

    public String getCourseNumber();

    public String getAlias();

    public String getTopPicSrc();

    public String getCourseDescribe();

    public String getLearningTime();

    public String getVideoPercentage();

    public String getJobsPercentage();

//    public String getCreateUser();

//    public String getCreateUserName();

    /** 课程类型*/
    public Integer getCourseType();
}