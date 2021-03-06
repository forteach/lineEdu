package com.project.portal.train.request;

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
@ApiModel(value = "培训保存修改财务文件信息")
public class FinanceDetailFileSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "fileId", value = "财务凭证资料编号")
    private String fileId;

    @ApiModelProperty(name = "fileName", value = "财务凭证资料名称")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "财务凭证资料URL")
    private String fileUrl;

    @ApiModelProperty(name = "pjPlanId", value = "财务凭证计划编号")
    private String pjPlanId;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}
