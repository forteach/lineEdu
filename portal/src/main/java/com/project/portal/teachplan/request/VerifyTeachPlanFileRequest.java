package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2020/1/13 15:03
 * @Version: 1.0
 * @Description:
 */
@Data
public class VerifyTeachPlanFileRequest implements Serializable {
    @ApiModelProperty(name = "fileId", value = "文件Id", dataType = "string")
    private String fileId;
    @ApiModelProperty(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", example = "0", dataType = "string", required = true)
    private String verifyStatus;
    @ApiModelProperty(name = "remark", value = "备注信息", dataType = "string")
    private String remark;
}
