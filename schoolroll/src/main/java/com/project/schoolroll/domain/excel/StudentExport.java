package com.project.schoolroll.domain.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-12 14:44
 * @version: 1.0
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentExport {
    /**"姓名",*/
    private String studentName;
    /**"性别",*/
    private String gender;
    /**"出生日期",*/
    private String stuBirthDate;
    /**"身份证件类型",*/
    private String stuCardType;
    /**"身份证件号",*/
    private String stuIDCard;
    /**"姓名拼音",*/
    private String namePinYin;
    /**"班级名称",*/
    private String className;
    /**"学号",*/
    private String studentId;
    /**"学生类别",*/
    private String studentCategory;
    /**"学习形式",*/
    private String learningModality;
    /**"入学方式",*/
    private String waysEnrollment;
    /**"就读方式",*/
    private String waysStudy;
    /**"国籍/地区",*/
    private String nationality;
    /**"港澳台侨外",*/
    private String nationalityType;
    /**"婚姻状况",*/
    private String marriage;
    /**"乘火车区间",*/
    private String trainSpace;
    /**"是否随迁子女",*/
    private String isImmigrantChildren;
    /**"生源地行政区划码",*/
    private String studentFormAdministrativeCode;
    /**"出生地行政区划码",*/
    private String birthplaceAdministrativeCode;
    /**"籍贯地行政区划码",*/
    private String nativePlaceFormAdministrativeCode;
    /**"户口所在地区县以下详细地址",*/
    private String householdAddressDetails;
    /**"所属派出所,"*/
    private String localPoliceStation;
    /**"户口所在地行政区划码",*/
    private String householdAdministrativeCode;
    /**"户口性质",*/
    private String householdType;
    /**"学生居住地类型",*/
    private String studentHabitationType;
    /**"入学年月",*/
    private String enrollmentDate;
    /**"专业简称",*/
    private String specialtyName;
    /**"学制",*/
    private String educationalSystem;
    /**"民族",*/
    private String nation;
    /**"政治面貌",*/
    private String politicalStatus;
    /**"健康状况",*/
    private String healthCondition;
    /**"学生来源",*/
    private String studentSource;
    /**"招生对象",*/
    private String recruit;
    /**"联系电话",*/
    private String stuPhone;
    /**"是否建档立卡贫困户",*/
    private String isDestituteFamily;
    /**"招生方式",*/
    private String studentRecruitingWays;
    /**"联招合作类型",*/
    private String jointRecruitmentCooperationType;
    /**"准考证号",*/
    private String entranceCertificateNumber;
    /**"考生号",*/
    private String candidateNumber;
    /**"考试总分",*/
    private String totalExaminationAchievement;
    /**"联招合作办学形式",*/
    private String jointRecruitmentCooperationStyle;
    /**"联招合作学校代码",*/
    private String jointRecruitmentCooperationSchoolCode;
    /**"校外教学点",*/
    private String offCampusTeachingAddress;
    /**"分段培养方式",*/
    private String stageByStageEducationType;
    /**"英文姓名",*/
    private String englishName;
    /**"电子信箱/其他联系方式",*/
    private String stuEmail;
    /**"家庭现地址",*/
    private String familyAddress;
    /**"家庭邮政编码",*/
    private String familyPostalCode;
    /**"家庭电话",*/
    private String familyPhone;
    /**"成员1姓名",*/
    private String family1Name;
    /**"成员1关系",*/
    private String family1Relationship;
    /**"成员1是否监护人",*/
    private String family1IsGuardian;
    /**"成员1联系电话",*/
    private String family1Phone;
    /**"成员1出生年月",*/
    private String family1BirthDate;
    /**"成员1身份证件类型",*/
    private String family1CardType;
    /**"成员1身份证件号",*/
    private String family1IDCard;
    /**"成员1民族",*/
    private String family1Nation;
    /**"成员1政治面貌",*/
    private String family1PoliticalStatus;
    /**"成员1健康状况",*/
    private String family1HealthCondition;
    /**"成员1工作或学习单位",*/
    private String family1CompanyOrganization;
    /**"成员1职务",*/
    private String family1Position;
    /**"成员2姓名",*/
    private String family2Name;
    /**"成员2关系",*/
    private String family2Relationship;
    /**"成员2是否监护人",*/
    private String family2IsGuardian;
    /**"成员2联系电话",*/
    private String family2Phone;
    /**"成员2出生年月",*/
    private String family2BirthDate;
    /**"成员2身份证件类型",*/
    private String family2CardType;
    /**"成员2身份证件号",*/
    private String family2IDCard;
    /**"成员2民族",*/
    private String family2Nation;
    /**"成员2政治面貌",*/
    private String family2PoliticalStatus;
    /**"成员2健康状况",*/
    private String family2HealthCondition;
    /**"成员2工作或学习单位",*/
    private String family2CompanyOrganization;
    /**"成员2职务"*/
    private String family2Position;
}