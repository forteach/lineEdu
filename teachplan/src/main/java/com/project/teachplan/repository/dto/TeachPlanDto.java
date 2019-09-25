package com.project.teachplan.repository.dto;

import javax.persistence.Column;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 17:33
 * @version: 1.0
 * @description:
 */
public interface TeachPlanDto {
    public String getPlanId();

    public String getPlanName();

    public String getStartDate();

    public String getEndDate();

    public String getPlanAdmin();

    public Integer getCourseNumber();

    public Integer getClassNumber();

    public Integer getSumNumber();

    public String getCenterAreaId();

    public String getCenterName();

    public String getIsValidated();

    public String getCreateTime();
}