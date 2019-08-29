package com.project.portal.train.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassFileFindByPjPlanIdRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号", dataType = "string", required = true)
    private String pjPlanId;
}
