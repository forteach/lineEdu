package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 11:26
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "教师保存修改信息对象")
public class TeacherSaveUpdateRequest implements Serializable {
    @ApiModelProperty(name = "teacherId", value = "教师id", dataType = "string")
    private String teacherId;

    @ApiModelProperty(name = "teacherName", value = "教师名称", dataType = "string")
    private String teacherName;

    @ApiModelProperty(name = "teacherCode", value = "教师代码", dataType = "string")
    private String teacherCode;

    @ApiModelProperty(name = "gender", value = "性别", dataType = "string")
    private String gender;

    @ApiModelProperty(name = "birthDate", value = "出生年月", dataType = "string")
    private String birthDate;

    @ApiModelProperty(name = "idCard", value = "身份证号", dataType = "string")
    private String idCard;

    @ApiModelProperty(name = "professionalTitle", value = "现任专业技术职务", dataType = "string")
    private String professionalTitle;

    @ApiModelProperty(name = "professionalTitleDate", value = "现任专业技术职务取得时间", dataType = "string")
    private String professionalTitleDate;

    @ApiModelProperty(name = "position", value = "工作单位及职务", dataType = "string")
    private String position;

    @ApiModelProperty(name = "industry", value = "所在行业", dataType = "string")
    private String industry;
    @ApiModelProperty(name = "email", value = "邮箱地址", dataType = "string")
    private String email;
    @ApiModelProperty(name = "phone", value = "联系电话", dataType = "string")
    private String phone;
    @ApiModelProperty(name = "specialty", value = "专业", dataType = "string")
    private String specialty;
    @ApiModelProperty(name = "isFullTime", value = "是否全日制(Y/N)", dataType = "string")
    private String isFullTime;
    @ApiModelProperty(name = "academicDegree", value = "学位", dataType = "string")
    private String academicDegree;
    @ApiModelProperty(name = "bankCardAccount", value = "银行卡账户", dataType = "string")
    private String bankCardAccount;
    @ApiModelProperty(name = "bankCardBank", value = "银行卡开户行", dataType = "string")
    private String bankCardBank;
}