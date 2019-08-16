package com.project.portal.train.request;

import com.project.portal.request.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;


/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description: 财务明细
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "财务明细添加修改")
public class FinanceDetailRequest extends BaseReq {


    @ApiModelProperty(name = "detailId", value = "培训财务流水编号")
    private String detailId;

    @ApiModelProperty(name = "financeTypeId", value = "培训财务类型Id")
    private String financeTypeId;

    @ApiModelProperty(name = "financeTypeName", value = "培训财务类型名称")
    private String financeTypeName;

    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号")
    private String pjPlanId;

    @ApiModelProperty(name = "pjPlanName", value = "培训项目计划名称")
    private String pjPlanName;

    @ApiModelProperty(name = "inOut", value = "收入支出")
    private String inOut;

    @ApiModelProperty(name = "money", value = "金额")
    private String money;
}