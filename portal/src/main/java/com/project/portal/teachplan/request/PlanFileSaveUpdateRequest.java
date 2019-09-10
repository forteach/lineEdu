package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/16 00:19
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "在线计划班级资料信息保存修改")
public class PlanFileSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "fileId", value = "培训资料编号", dataType = "string")
    private String fileId;

    @ApiModelProperty(name = "fileName", value = "培训资料名称", dataType = "string")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "培训资料URL", dataType = "string")
    private String fileUrl;

    @ApiModelProperty(name = "classId", value = "培训班级编号", dataType = "string")
    private String classId;

    /**
     * 项目id
     */
    @ApiModelProperty(name = "pjPlanId", value = "项目计划id", dataType = "string")
    private String planId;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}