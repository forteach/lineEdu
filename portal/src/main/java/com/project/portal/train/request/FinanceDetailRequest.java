package com.project.portal.train.request;

import com.project.portal.request.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;


/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description: 财务明细
 */
@Data
@ApiModel(value = "财务明细添加修改")
public class FinanceDetailRequest extends BaseReq {


    @Column(name = "detail_id", columnDefinition = "VARCHAR(40) COMMENT '培训财务流水编号'")
    @ApiModelProperty(name = "trainProjectId", value = "培训财务流水编号")
    private String detailId;

    @Column(name = "finance_type_id", columnDefinition = "VARCHAR(40) COMMENT '培训财务类型Id'")
    @ApiModelProperty(name = "trainProjectId", value = "培训财务类型Id")
    private String financeTypeId;

    @Column(name = "finance_type_name", columnDefinition = "VARCHAR(40) COMMENT '培训财务类型名称'")
    @ApiModelProperty(name = "trainProjectId", value = "培训财务类型名称")
    private String financeTypeName;


    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    @ApiModelProperty(name = "trainProjectId", value = "培训项目计划编号")
    private String pjPlanId;

    @Column(name = "pj_plan_name", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划名称'")
    @ApiModelProperty(name = "trainProjectId", value = "培训项目计划名称")
    private String pjPlanName;

    @Column(name = "in_out", columnDefinition = "VARCHAR(2) COMMENT '收入支出'")
    @ApiModelProperty(name = "trainProjectId", value = "收入支出")
    private String inOut;

    @Column(name = "money", columnDefinition = "int COMMENT '金额'")
    @ApiModelProperty(name = "trainProjectId", value = "金额")
    private String money;


}
