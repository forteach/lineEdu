package com.project.portal.databank.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 15:56
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "审核课程信息对象")
public class CourseVerifyRequest implements Serializable {
    @ApiModelProperty(name = "id", value = "需要审核的id", dataType = "string", required = true)
    private String id;
    @ApiModelProperty(name = "verifyStatus", value = "修改状态", dataType = "0 (同意) 1 (已经提交) 2 (不同意)", required = true)
    private String verifyStatus;
    @ApiModelProperty(name = "remark", value = "备注", dataType = "string")
    private String remark;
}