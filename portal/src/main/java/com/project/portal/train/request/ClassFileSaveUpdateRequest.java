package com.project.portal.train.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/16 00:19
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "培训 班级资料信息保存修改")
public class ClassFileSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "fileId", value = "培训资料编号", dataType = "string")
    private String fileId;

    @ApiModelProperty(name = "fileName", value = "培训资料名称", dataType = "string")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "培训资料URL", dataType = "string")
    private String fileUrl;

    @ApiModelProperty(name = "classId", value = "培训班级编号", dataType = "string")
    private String classId;
}