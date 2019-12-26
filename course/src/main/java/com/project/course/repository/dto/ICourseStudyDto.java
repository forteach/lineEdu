package com.project.course.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-6 12:23
 * @version: 1.0
 * @description:
 */
public interface ICourseStudyDto {
    public String getCourseId();

    public String getCourseName();

    public String getCourseNumber();

    public String getAlias();

    public String getTopPicSrc();

    public String getCourseDescribe();

    public String getCourseType();

    public String getVideoPercentage();

    public String getJobsPercentage();

    public Integer getOnLineTime();

    public Integer getOnLineTimeSum();

    public Integer getAnswerSum();

    public Integer getCorrectSum();
}