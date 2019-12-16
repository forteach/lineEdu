package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 18:46
 * @version: 1.0
 * @description:
 */
@Data
public class LearnCenterFileSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "fileName", value = "文件名称", dataType = "string")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "文件url", dataType = "string", required = true)
    private String fileUrl;

    @ApiModelProperty(name = "fileType", value = "文件类型", dataType = "string")
    private String fileType;

    @ApiModelProperty(name = "type", value = "文件资料类型，A企业资质, B法人身份信息, C其他")
    private String type;

    @ApiModelProperty(name = "centerId", value = "学习中心id", dataType = "string")
    private String centerId;
}