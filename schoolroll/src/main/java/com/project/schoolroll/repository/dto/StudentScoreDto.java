package com.project.schoolroll.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-9 14:55
 * @version: 1.0
 * @description:
 */
public interface StudentScoreDto {
    /** 学号*/
    public String getStudentId();

    public String getStuId();
    /** 姓名*/
    public String getStudentName();
    /** 性别*/
    public String getGender();
    /** 课程名称*/
    public String getCourseName();
    /** 学年*/
    public String getSchoolYear();
    /** 学期*/
    public String getTerm();
    /** 课程分数*/
    public Float getCourseScore();
    /** 线上成绩*/
    public String getOnLineScore();
    /** 线下成绩*/
    public String getOffLineScore();
    /** 课程类别*/
    public String getCourseType();

    public String getType();
}