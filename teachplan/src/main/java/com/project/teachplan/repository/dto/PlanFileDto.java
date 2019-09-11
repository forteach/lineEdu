package com.project.teachplan.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-11 14:40
 * @version: 1.0
 * @description:
 */
public interface PlanFileDto {
    /** 文件url*/
    public String getFileId();
    /** 文件名称*/
    public String getFileName();
    /** 文件url*/
    public String getFileUrl();
    /*×班级id*/
    public String getClassId();
    /** 班级名称*/
    public String getClassName();
    /**计划Id*/
    public String getPlanId();
    /**计划名称*/
    public String getPlanName();
    /** 计划结束时间*/
    public String getStartDate();
    /*× 计划结束时间*/
    public String getEndDate();
    /** 计划负责人*/
    public String getPlanAdmin();
}