package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 10:55
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "保存修改学习中心信息")
public class LearnCenterSaveUpdateRequest implements Serializable {
    /**
     * 学习中心id
     */
    @ApiModelProperty(name = "centerId", value = "学习中心编号", dataType = "string")
    private String centerId;
    /**
     * 学习中心名称
     */
    @ApiModelProperty(name = "centerName", value = "学习中心名称", dataType = "string", required = true)
    private String centerName;

    /**
     * 学习中心地址
     */
    @ApiModelProperty(name = "address", value = "地址", dataType = "string", required = true)
    private String address;
    /**
     * 负责人
     */
    @ApiModelProperty(name = "principal", value = "企业负责人", dataType = "string", required = true)
    private String principal;
    /**
     * 联系电话
     */
    @ApiModelProperty(name = "phone", value = "负责人电话", dataType = "string", required = true)
    private String phone;

    @ApiModelProperty(name = "bankName", value = "银行名称", dataType = "string")
    private String bankName;
    /**
     * 银行账户
     */
    @ApiModelProperty(name = "bankingAccount", value = "银行账户", dataType = "string")
    private String bankingAccount;
    /**
     * 开户人
     */
    @ApiModelProperty(name = "accountHolder", value = "开户人", dataType = "string")
    private String accountHolder;
    /**
     * 开户人电话
     */
    @ApiModelProperty(name = "accountHolderPhone", value = "开户人电话", dataType = "string")
    private String accountHolderPhone;
    /**
     * 开户行地址
     */
    @ApiModelProperty(name = "bankingAccountAddress", value = "开户行地址", dataType = "string")
    private String bankingAccountAddress;

    @ApiModelProperty(name = "companyAddress", value = "公司地址", dataType = "string")
    private String companyAddress;

    @ApiModelProperty(name = "companyName", value = "公司企业名称", dataType = "string")
    private String companyName;

    @ApiModelProperty(name = "schoolAdmin", value = "校内联系人", dataType = "string", required = true)
    private String schoolAdmin;

    @ApiModelProperty(name = "schoolPhone", value = "校内联系人电话", dataType = "string", required = true)
    private String schoolPhone;

    @ApiModelProperty(name = "endDate", value = "结束时间", dataType = "string", required = true)
    private String endDate;
}