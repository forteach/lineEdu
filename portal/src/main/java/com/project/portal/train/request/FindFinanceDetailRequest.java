package com.project.portal.train.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindFinanceDetailRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "pjPlanId", value = "财务凭证计划编号")
    private String pjPlanId;
    @ApiModelProperty(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "string")
    private String agoDay;
}
