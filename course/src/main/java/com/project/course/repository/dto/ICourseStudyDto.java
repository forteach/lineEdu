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

    public Integer getOnLineTime();

    public Integer getOnLineTimeSum();

    public Integer getAnswerSum();

    public Integer getCorrectSum();
}