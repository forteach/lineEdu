package com.project.portal.train.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/16 00:03
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "培训中心分页查询财务")
public class FinanceTypeSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "financeTypeId", value = "培训财务类型编号", dataType = "string")
    private String financeTypeId;

    @ApiModelProperty(name = "financeTypeName", value = "培训财务类型名称", dataType = "string")
    private String financeTypeName;
}
