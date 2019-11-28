package com.project.portal.teachplan.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanFileFindByPlanIdRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "planId", value = "项目计划编号", dataType = "string", required = true)
    private String planId;
}
