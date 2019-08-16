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
@ApiModel(value = "培训保存修改财务详情信息")
public class FinanceDetailSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "detailId", value = "培训财务流水编号", dataType = "string")
    private String detailId;

    @ApiModelProperty(name = "financeTypeId", value = "培训财务类型Id", dataType = "string")
    private String financeTypeId;

    @ApiModelProperty(name = "financeTypeName", value = "培训财务类型名称", dataType = "string")
    private String financeTypeName;

    @ApiModelProperty(name = "trainClassId", value = "培训项目班级编号", dataType = "string")
    private String trainClassId;

    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号", dataType = "string")
    private String pjPlanId;

    @ApiModelProperty(name = "pjPlanName", value = "培训项目计划名称", dataType = "string")
    private String pjPlanName;

    @ApiModelProperty(name = "inOut", value = "收入支出", dataType = "string")
    private String inOut;

    @ApiModelProperty(name = "money", value = "金额", dataType = "string")
    private String money;

    @ApiModelProperty(name = "batches", value = "账目批次", dataType = "string")
    private String batches;

    @ApiModelProperty(name = "createYear", value = "创建年份", dataType = "string")
    private String createYear;

    @ApiModelProperty(name = "createMonth", value = "培训财务类型Id", dataType = "string")
    private String createMonth;
}