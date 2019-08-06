package com.project.schoolroll.domain.excel;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-23 16:34
 * @version: 1.0
 * @description:
 */
public enum StudentEnum {

    /** 姓名*/
    studentName("姓名"),
    /** 性别*/
    gender("性别"),
    /** 出生日期*/
    stuBirthDate("出生日期"),
    /** 身份证件类型*/
    stuCardType("身份证件类型"),
    /** 身份证件号*/
    stuIDCard("身份证件号"),
    /** 姓名拼音*/
    namePinYin("姓名拼音"),
    /** 班级名称*/
    className("班级名称"),
    /** 学号*/
    studentId("学号"),
    /** 学生类别*/
    studentCategory("学生类别"),
    /** 学习形式*/
    learningModality("学习形式"),
    /** 入学方式*/
    waysEnrollment("入学方式"),
    /** 就读方式*/
    waysStudy("就读方式"),
    /** 国籍/地区 */
    nationality("国籍/地区"),
    /** 港澳台侨外*/
    nationalityType("港澳台侨外"),
    /** 婚姻状况*/
    marriage("婚姻状况"),
    /** 乘火车区间 */
//    trainSpace("乘火车区间"),
    /** 是否随迁子女*/
    isImmigrantChildren("是否随迁子女"),

    /** 出生地行政区划码*/
//    /** 户口所在地行政区划码*/
//    householdAdministrativeCode("户口所在地行政区划码"),
    /** 户口性质*/
    householdType("户口性质"),
//    /** 学生居住地类型*/
//    studentHabitationType("学生居住地类型"),
    /** 入学年月,报道日期*/
//    arrivalDate("入学年月"),
    /** 专业简称*/
    specialtyName("专业简称"),
    /** 学制*/
    educationalSystem("学制"),
    /** 民族*/
    nation("民族"),
    /** 政治面貌*/
    politicalStatus("政治面貌"),
//    /** 健康状况*/
//    healthCondition("健康状况"),
    /** 联系电话 */
    stuPhone("联系电话"),
    /** 准考证号 */
    entranceCertificateNumber("准考证号"),
    /** 考生号*/
    candidateNumber("考生号"),
    /** 考试总分*/
    totalExaminationAchievement("考试总分"),
    /*× 入学年月*/
    enrollmentDate("入学年月"),
    /** 招生批次 招生批次 春季,秋季*/
    recruitBatch("招生批次"),

    /** 家庭成员1信息 */

    family1Name("成员1姓名"),
    family1Relationship("成员1关系"),
    family1IsGuardian("成员1是否监护人"),
    family1Phone("成员1联系电话"),
    family1BirthDate("成员1出生年月"),
    family1CardType("成员1身份证件类型"),
    family1IDCard("成员1身份证件号"),
    family1Nation("成员1民族"),
    family1PoliticalStatus("成员1政治面貌"),
    family1HealthCondition("成员1健康状况"),
    family1CompanyOrganization("成员1工作或学习单位"),
    family1Position("成员1职务"),

    /** 成员2 信息字段*/

    family2Name("成员2姓名"),
    family2Relationship("成员2关系"),
    family2IsGuardian("成员2是否监护人"),
    family2Phone("成员2联系电话"),
    family2BirthDate("成员2出生年月"),
    family2CardType("成员2身份证件类型"),
    family2IDCard("成员2身份证件号"),
    family2Nation("成员2民族"),
    family2PoliticalStatus("成员2政治面貌"),
    family2HealthCondition("成员2健康状况"),
    family2CompanyOrganization("成员2工作或学习单位"),
    family2Position("成员2职务"),



    /** 扩展字段 */

    /** 乘火车区间*/
    trainSpace("乘火车区间"),
    /** 生源地行政区划码*/
    studentFormAdministrativeCode("生源地行政区划码"),
    /** 出生地行政区划码*/
    birthplaceAdministrativeCode("出生地行政区划码"),
    /** 籍贯地行政区划码*/
    nativePlaceFormAdministrativeCode("籍贯地行政区划码"),
    /** 家庭现地址*/
    familyAddress("家庭现地址"),
    /** 家庭邮政编码*/
    familyPostalCode("家庭邮政编码"),
    /** 家庭电话*/
    familyPhone("家庭电话"),
    /** 户口所在地行政区划码 */
    householdAdministrativeCode("户口所在地行政区划码"),
    /** 户口所在地区县以下详细地址 */
    householdAddressDetails("户口所在地区县以下详细地址"),
    /** 所属派出所*/
    localPoliceStation("所属派出所"),
    /** 学生居住地类型*/
    studentHabitationType("学生居住地类型"),
    /** 健康状况*/
//    healthyStatus("健康状况"),
    /** 健康状况*/
    healthCondition("健康状况"),
    /** 学生来源*/
    studentSource("学生来源"),
    /** 招生对象*/
    recruit("招生对象"),
    /** 毕业学校*/
    school("毕业学校"),
    /** 是否建档立卡贫困户*/
    isDestituteFamily("是否建档立卡贫困户"),
    /** 招生方式*/
    studentRecruitingWays("招生方式"),
    /** 联招合作类型*/
    jointRecruitmentCooperationType("联招合作类型"),
    /** 考生特长*/
    studentSpeciality("考生特长"),
    /** 考生既往病史*/
    studentMedicalHistory("考生既往病史"),
    /** 体检结论*/
    healthReport("体检结论"),
    /** 联招合作办学形式*/
    jointRecruitmentCooperationStyle("联招合作办学形式"),
    /** 联招合作学校代码*/
    jointRecruitmentCooperationSchoolCode("联招合作学校代码"),
    /** 校外教学点*/
    offCampusTeachingAddress("校外教学点"),
    /** 分段培养方式*/
    stageByStageEducationType("分段培养方式"),
    /** 英文姓名*/
    englishName("英文姓名"),
    /** 电子信箱/其他联系方式 */
    stuEmail("电子信箱/其他联系方式"),
    /** 备注*/
    remark("备注")
    ;

    private String name;


    public String getName() {
        return name;
    }

    StudentEnum(String name) {
        this.name = name;
    }

    StudentEnum() {
    }
}
