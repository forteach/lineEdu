package com.project.teachplan.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2019/12/18 18:00
 * @version: 1.0
 * @description:
 */
public interface TeachCenterDto {
    /**
     * 学习中心Id
     */
    public String getCenterAreaId();

    /**
     * 学习中心Id
     */
    public String getCenterName();

    /**
     * 计划Id
     */
    public String getPlanId();

    /**
     * 计划名称planeName
     */
    public String getPlanName();

    /**
     * 开始时间
     */
    public String getStartDate();

    /**
     * 结束时间
     */
    public String getEndDate();

    public String getClassId();
}
