package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeachPlanSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "planId", dataType = "string", value = "计划id")
    private String planId;

    @ApiModelProperty(name = "planName", dataType = "string", value = "计划名称")
    private String planName;

    @ApiModelProperty(name = "planAdmin", dataType = "string", value = "计划负责人")
    private String planAdmin;

    @ApiModelProperty(name = "startDate", dataType = "string", value = "计划结束时间")
    private String startDate;

    @ApiModelProperty(name = "endDate", dataType = "string", value = "计划结束时间")
    private String endDate;
    @ApiModelProperty(name = "specialtyName", value = "专业名称", dataType = "string")
    private String specialtyName;
    @ApiModelProperty(name = "grade", value = "年级", dataType = "string")
    private String grade;
}