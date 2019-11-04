package com.project.teachplan.repository.dto;

public interface PlanCourseStudyDto {
    /** 学生Id*/
    public String getStudentId();
    /** 学生名称*/
    public String getStudentName();
    /** 电话*/
    public String getPhone();
    /** 学习中心Id*/
    public String etCenterAreaId();
    /** 学习中心Id*/
    public String getCenterName();
    /** 计划Id*/
    public String getPlanId();
    /** 计划名称*/
    public String getPlanName();
    /** 开始时间*/
    public String getStartDate();
    /** 结束时间*/
    public String getEndDate();
}