package com.project.portal.train.request;

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
public class FinanceDetailFileSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "fileId", value = "财务凭证资料编号")
    private String fileId;

    @ApiModelProperty(name = "fileName", value = "财务凭证资料名称")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "财务凭证资料URL")
    private String fileUrl;

    @ApiModelProperty(name = "planId", value = "财务凭证计划编号")
    private String planId;
}
