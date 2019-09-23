package com.project.teachplan.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-23 11:30
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

    public String getClassId();

    public String getPlanId();
}
