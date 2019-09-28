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
    @ApiModelProperty(name = "centerAreaId", dataType = "string", hidden = true)
    private String centerAreaId;
    @ApiModelProperty(name = "verifyStatus", dataType = "string", value = "0 (同意) 1 (已经提交) 2 (不同意)")
    private String verifyStatus;
}