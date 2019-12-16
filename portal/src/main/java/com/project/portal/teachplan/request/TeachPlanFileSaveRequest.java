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
@ApiModel(value = "教学计划资料信息保存")
public class TeachPlanFileSaveRequest implements Serializable {

    @ApiModelProperty(name = "fileName", value = "资料名称", dataType = "string")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "资料URL", dataType = "string")
    private String fileUrl;

    @ApiModelProperty(name = "fileType", value = "资料类型", dataType = "string")
    private String fileType;

    @ApiModelProperty(name = "type", value = "资料类型，上传分类 A.大纲、B.教材库、C.课表,D 素材库", dataType = "string")
    private String type;

    /**
     * 项目id
     */
    @ApiModelProperty(name = "planId", value = "项目计划id", dataType = "string")
    private String planId;
}