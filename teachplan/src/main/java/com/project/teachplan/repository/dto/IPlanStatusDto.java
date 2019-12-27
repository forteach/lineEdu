package com.project.teachplan.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 12:05
 * @version: 1.0
 * @description:
 */
public interface IPlanStatusDto {
    public String getPlanId();
    /** 计划完成状态 0 完成 1 未完成/进行中*/
    public Integer getStatus();
    /** */
    public Integer getCountStatus();
}
