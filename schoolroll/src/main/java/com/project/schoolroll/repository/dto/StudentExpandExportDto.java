package com.project.schoolroll.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-6 14:59
 * @version: 1.0
 * @description:
 */
public interface StudentExpandExportDto {
    /** 扩展字段名称*/
    public String getExpandName();
    /*× 扩展字段值*/
    public String getExpandValue();
}