package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 15:19
 * @version: 1.0
 * @description:
 */
@Data
public class TeachPlanVerifyRequest implements Serializable {
    @ApiModelProperty(name = "planId", value = "计划Id", dataType = "string", required = true)
    private String planId;
    @ApiModelProperty(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", required = true)
    private String verifyStatus;
    @ApiModelProperty(name = "remark", value = "备注信息", dataType = "string")
    private String remark;
}
