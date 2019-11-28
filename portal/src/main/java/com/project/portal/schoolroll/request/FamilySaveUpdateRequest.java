package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "学生家庭成员添加修改")
public class FamilySaveUpdateRequest implements Serializable {
    /**
     * 家庭成员id
     */
    @ApiModelProperty(name = "familyId", value = "家庭成员id")
    private String familyId;
    /**
     * 学生id
     */
    @ApiModelProperty(name = "studentId", value = "学生id")
    private String studentId;
    /**
     * 家庭姓名
     */
    @ApiModelProperty(name = "familyName", value = "姓名")
    private String familyName;
    /**
     * 家庭成员关系
     * 父亲，母亲，其他亲属
     */
    @ApiModelProperty(name = "familyRelationship", value = "家庭成员关系")
    private String familyRelationship;
    /**
     * 联系电话
     */
    @ApiModelProperty(name = "familyPhone", value = "联系电话")
    private String familyPhone;
    /**
     * 是否是监护人
     */
    @ApiModelProperty(name = "isGuardian", value = "是否是监护人 (Y/N)")
    private String isGuardian;
    /**
     * 身份证件类型
     */
    @ApiModelProperty(name = "familyCardType", value = "身份证件类型")
    private String cardType;
    /**
     * 身份证号
     */
    @ApiModelProperty(name = "familyIDCard", value = "身份证号")
    private String familyIDCard;
    /**
     * 出生日期
     */
    @ApiModelProperty(name = "birthDate", value = "出生日期")
    private String familyBirthDate;
    /**
     * 健康状态
     * 健康/良好
     * 一般/较弱
     * 残疾
     */
    @ApiModelProperty(name = "healthCondition", value = "健康状态")
    private String healthCondition;
    /**
     * 工作单位
     */
    @ApiModelProperty(name = "companyOrganization", value = "工作单位")
    private String companyOrganization;
    /**
     * 政治面貌
     */
    @ApiModelProperty(name = "politicalStatus", value = "政治面貌")
    private String politicalStatus;
}
