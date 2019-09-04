package com.project.portal.teachplan.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeachPlanPageAllRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "planId", dataType = "string", value = "计划id")
    private String planId;
}
