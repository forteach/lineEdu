package com.project.schoolroll.domain.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-18 15:29
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentImport implements Serializable {
    /**
     * 学生学号id
     */
    private String studentId;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 学生身份证号码
     */
    private String stuIDCard;
    /**
     * 入学年度
     */
    private String enrollmentDate;
    /**
     * 招生批次 (招募批次)
     */
    private String recruitBatch;
    /**
     * 专业名称
     */
    private String specialtyName;
    /**
     * 政治面貌
     */
    private String politicalStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 学制及计划类别
     */
    private String educationalSystem;
    /**
     * 中招考试成绩/考试成绩
     */
    private String totalExaminationAchievement;
    /**
     * 准考证号
     */
    private String entranceCertificateNumber;
    /**
     * 考试市县/地区
     */
    private String examinationArea;
    /**
     * 毕业学校地区
     */
    private String school;
    /**
     * 就读方式/学习方式
     * 走读,住校,借宿,其它
     */
    private String waysStudy;
    /**
     * 报道日期
     */
//    private String arrivalDate;
    /**
     * 家长姓名
     */
//    private String familyName;
    /**
     * 家庭电话
     */
    private String familyPhone;
    /**
     * 工作单位
     */
//    private String companyOrganization;
    /**
     * 学生电话
     */
    private String stuPhone;
    /**
     * 学生邮箱
     */
    private String stuEmail;
    /**
     * 家庭现住址
     */
    private String familyAddress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 乘火车区间
     */
    private String trainSpace;
    /**
     * 生源地行政区划码
     */
    private String studentFormAdministrativeCode;
    /**
     * 家庭邮政编码
     */
    private String familyPostalCode;
    /**
     * 户口所在地行政代码
     * @return
     */
    private String householdAdministrativeCode;
    /**
     * 户口所在地县以下详细地址
     */
    private String householdAddressDetails;
    /**
     * 所属派出所
     */
    private String localPoliceStation;
    /**
     * 学生居住地类型
     */
    private String studentHabitationType;
    /**
     * 健康状况
     */
//    private String healthyStatus;
    /**
     * 学生来源
     */
    private String studentSource;
    /**
     * 招生对象
     */
    private String recruit;
    /**
     * 是否建档立卡贫困户
     */
    private String isDestituteFamily;
    /**
     * 招生方式
     */
    private String studentRecruitingWays;
    /**
     * 联招合作类型
     */
    private String jointRecruitmentCooperationType;
    /**
     * 考生特长
     */
    private String studentSpeciality;
    /**
     * 考生既往病史
     */
    private String studentMedicalHistory;
    /**
     * 体检结论
     */
    private String healthReport;
    /**
     * 联招合作办学形式
     */
    private String jointRecruitmentCooperationStyle;
    /**
     * 联招合作学校代码
     */
    private String jointRecruitmentCooperationSchoolCode;
    /**
     * 校外教学点
     */
    private String offCampusTeachingAddress;
    /**
     * 分阶段培养方式
     */
    private String stageByStageEducationType;
    /**
     * 英文姓名
     */
    private String englishName;
    /**
     * 家庭成员1姓名
     */
    private String family1Name;
    /**
     * 家庭成员1关系
     */
    private String family1Relationship;
    /**
     * 成员1是否是监护人
     */
    private String family1IsGuardian;
    /**
     * 成员1联系电话
     */
    private String family1Phone;
    /**
     * 家庭成员1出生年月
     */
    private String family1BirthDate;
    /**
     * 成员1身份证件类型
     */
    private String family1CardType;
    /**
     * 成员1身份证号
     */
    private String family1IDCard;
    /**
     * 家庭成员1民族
     */
    private String family1Nation;
    /**
     * 成员1政治面貌
     */
    private String family1PoliticalStatus;
    /**
     * 家庭成员1健康状态
     */
    private String family1HealthCondition;
    /**
     * 成员1工作学习单位
     */
    private String family1CompanyOrganization;
    /**
     * 成员1职务
     */
    private String family1Position;
    /**
     * 家庭成员2姓名
     */
    private String family2Name;
    /**
     * 家庭成员2关系
     */
    private String family2Relationship;
    /**
     * 成员2是否是监护人
     */
    private String family2IsGuardian;
    /**
     * 成员2联系电话
     */
    private String family2Phone;
    /**
     * 家庭成员2出生年月
     */
    private String family2BirthDate;
    /**
     * 成员2身份证件类型
     */
    private String family2CardType;
    /**
     * 成员2身份证号
     */
    private String family2IDCard;
    /**
     * 家庭成员2民族
     */
    private String family2Nation;
    /**
     * 成员2政治面貌
     */
    private String family2PoliticalStatus;
    /**
     * 家庭成员2健康状态
     */
    private String family2HealthCondition;
    /**
     * 成员2工作学习单位
     */
    private String family2CompanyOrganization;
    /**
     * 成员2职务
     */
    private String family2Position;
    /**
     * 学生生日
     */
    private String stuBirthDate;
    /**
     * 学生身份证件类型
     */
    private String stuCardType;
    /**
     * 学生姓名拼音
     */
    private String namePinYin;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 学生类别
     */
    private String studentCategory;
    /**
     * 学习形式
     */
    private String learningModality;
    /**
     * 入学方式
     */
    private String waysEnrollment;
    /**
     * 国籍/地区
     */
    private String nationality;
    /**
     * 港澳台侨外
     */
    private String nationalityType;
    /**
     * 婚姻状况
     */
    private String marriage;
    /**
     * 是随迁子女
     */
    private String isImmigrantChildren;
    /**
     * 出生地行政区划码
     */
    private String birthplaceAdministrativeCode;
    /**
     * 籍贯地区行政划码
     */
    private String nativePlaceFormAdministrativeCode;
    /**
     * 户口性质
     */
    private String householdType;
    /**
     * 健康状况
     */
    private String healthCondition;
    /**
     * 考生号
     */
    private String candidateNumber;
}