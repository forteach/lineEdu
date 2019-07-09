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
    @ApiModelProperty(name = "centerName", value = "学习中心名称", dataType = "string")
    private String centerName;

    /**
     * 学习中心地址
     */
    @ApiModelProperty(name = "address", value = "地址", dataType = "string")
    private String address;
    /**
     * 负责人
     */
    @ApiModelProperty(name = "principal", value = "负责人", dataType = "string")
    private String principal;
    /**
     * 联系电话
     */
    @ApiModelProperty(name = "phone", value = "负责人电话", dataType = "string")
    private String phone;
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
}
