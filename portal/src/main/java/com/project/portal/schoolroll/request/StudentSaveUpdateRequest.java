package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:27
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "保存修改学生信息")
public class StudentSaveUpdateRequest implements Serializable {

    /**
     * 学生Id/学号
     */
    @ApiModelProperty(name = "stuId", value = "学生id(学号)", dataType = "string")
    private String stuId;
    /**
     * 学生姓名
     */
    @ApiModelProperty(name = "stuName", value = "学生名字", dataType = "string")
    private String stuName;
    /**
     * 性别
     */
    @ApiModelProperty(name = "gender", value = "性别(男/女)", dataType = "string")
    private String gender;
    /**
     * 学生拼音
     */
    @ApiModelProperty(name = "namePinYin", value = "姓名拼音", dataType = "string")
    private String namePinYin;
    /**
     * 身份证件类型
     */
    @ApiModelProperty(name = "cardType", value = "身份证类型", dataType = "string")
    private String cardType;
    /**
     * 身份证件号
     */
    @ApiModelProperty(name = "IDCard", value = "身份证件号", dataType = "string")
    private String IDCard;
    /**
     * 联系电话
     */
    @ApiModelProperty(name = "phone", value = "联系电话", dataType = "string")
    private String phone;
    /**
     * 出生日期(年/月)
     */
    @ApiModelProperty(name = "birthDate", value = "出生日期(年/月)", dataType = "string")
    private String birthDate;
    /**
     * 国籍地区
     */
    @ApiModelProperty(name = "nationality", value = "国籍/地区", dataType = "string")
    private String nationality;
    /**
     * 港澳台侨外绩人士
     */
    @ApiModelProperty(name = "nationalityType", value = "港澳台侨外", dataType = "string")
    private String nationalityType;
    /**
     * 婚姻状态
     */
    @ApiModelProperty(name = "marriage", value = "婚姻状态", dataType = "string")
    private String marriage;
    /**
     * 民族
     */
    @ApiModelProperty(name = "nation", value = "民族", dataType = "string")
    private String nation;
    /**
     * 政治面貌
     */
    @ApiModelProperty(name = "politicalStatus", value = "政治面貌", dataType = "string")
    private String politicalStatus;
    /**
     * 户口性质
     */
    @ApiModelProperty(name = "householdType", value = "户口性质/类型", dataType = "string")
    private String householdType;
    /**
     * 是否随迁子女
     */
    @ApiModelProperty(name = "isImmigrantChildren", value = "是否随迁子女", dataType = "string")
    private String isImmigrantChildren;
    /**
     * 学习中心id
     */
    @ApiModelProperty(name = "centerId", value = "学习中心id", dataType = "string")
    private String centerId;
    /**
     * 学习类别
     */
    @ApiModelProperty(name = "studentCategory", value = "学习类别", dataType = "string")
    private String studentCategory;
    /**
     * 班级id
     */
    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string")
    private String classId;
    /**
     * 班级名称
     */
    @ApiModelProperty(name = "className", value = "班级名称", dataType = "string")
    private String className;
    /**
     * 专业简称
     */
    @ApiModelProperty(name = "specialtyName", value = "专业简称(名称)", dataType = "string")
    private String specialtyName;
    /**
     * 学制
     */
    @ApiModelProperty(name = "educationalSystem", value = "学制", dataType = "string")
    private String educationalSystem;
    /**
     * 就读方式/学习方式
     */
    @ApiModelProperty(name = "waysStudy", value = "就读方式/学习方式", dataType = "string")
    private String waysStudy;
    /**
     * 学习形式
     */
    @ApiModelProperty(name = "learningModality", value = "学习形式", dataType = "string")
    private String learningModality;
    /**
     * 入学方式
     */
    @ApiModelProperty(name = "waysEnrollment", value = "入学方式", dataType = "string")
    private String waysEnrollment;
    /**
     * 准考证号码
     */
    @ApiModelProperty(name = "entranceCertificateNumber", value = "准考证号码", dataType = "string")
    private String entranceCertificateNumber;
    /**
     * 考生号码
     */
    @ApiModelProperty(name = "candidateNumber", value = "考生号码", dataType = "string")
    private String candidateNumber;
    /**
     * 考试总成绩
     */
    @ApiModelProperty(name = "totalExaminationAchievement", value = "考试总成绩", dataType = "string")
    private String totalExaminationAchievement;
    /**
     * 入学时间(年/月)
     */
    @ApiModelProperty(name = "enrollmentDate", value = "入学时间(年/月)", dataType = "string")
    private String enrollmentDate;
    /**
     * 备注
     */
    @ApiModelProperty(name = "remark", value = "备注")
    private String remark;
}
