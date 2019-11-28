package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeachPlanFileFinaAllRequest implements Serializable {
    @ApiModelProperty(name = "planId", value = "计划id", dataType = "string", required = true)
    private String planId;
    @ApiModelProperty(name = "verifyStatus", value = "审核状态 0 已经审核, 1 没有审核 2 拒绝", dataType = "string")
    private String verifyStatus;
}