package com.project.schoolroll.domain.excel;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-26 18:04
 * @version: 1.0
 * @description:
 */
public class Dic {
    /**
     * 导入学生信息 redis key
     */
    public final static String IMPORT_STUDENTS = "importStudents";
    public final static String IMPORT_STUDENTS_ONLINE = "importStudentsOnLine";
    public final static String IMPORT_CLASS_FREE = "classFee";
    public final static String EXPORT_EXCEL_PREFIX = "$studentExport";

    public final static String IMPORT_COURSE_SCORE = "importCourseScore$";

    /** 学生数据状态 学生信息 0 表格导入, 1 手动添加*/
    public final static int STUDENT_ON_LINE_IMPORT_STATUS_IMPORT = 0;
    public final static int STUDENT_ON_LINE_IMPORT_STATUS_SAVE = 1;

//    private final static Map<Object, Object> EXPORT_STUDENT_MAP = MapUtil.builder()
//            .put(studentName.name(), studentName.getName())
//            .put(gender.name(), gender.getName())
//            .put(stuBirthDate.name(), stuBirthDate.getName())
//            .put(stuCardType.name(), stuCardType.getName())
//            .put(stuIDCard.name(), stuIDCard.getName())
//            .put(namePinYin.name(), namePinYin.getName())
//            .put(className.name(), className.getName())
//            .put(studentId.name(), studentId.getName())
//            .put(studentCategory.name(), studentCategory.getName())
//            .put(learningModality.name(), learningModality.getName())
//            .put(waysEnrollment.name(), waysEnrollment.getName())
//            .put(waysStudy.name(), waysStudy.getName())
//            .put(nationality.name(), nationality.getName())
//            .put(nationalityType.name(), nationalityType.getName())
//            .put(marriage.name(), marriage.getName())
//            .put(isImmigrantChildren.name(), isImmigrantChildren.getName())
//            .put(householdType.name(), householdType.getName())
//            .put(specialtyName.name(), specialtyName.getName())
//            .put(educationalSystem.name(), educationalSystem.getName())
//            .put(nation.name(), nation.getName())
//            .put(politicalStatus.name(), politicalStatus.getName())
//            .put(stuPhone.name(), stuPhone.getName())
//            .put(entranceCertificateNumber.name(), entranceCertificateNumber.getName())
//            .put(candidateNumber.name(), candidateNumber.getName())
//            .put(totalExaminationAchievement.name(), totalExaminationAchievement.getName())
//
//            .put(familyName.name(), familyName.getName())
//            .put(familyRelationship.name(), familyRelationship.getName())
//            .put(familyIsGuardian.name(), familyIsGuardian.getName())
//            .put(familyPhone.name(), telephone.getName())
//            .put(familyBirthDate.name(), familyBirthDate.getName())
//            .put(familyCardType.name(), familyCardType.getName())
//            .put(familyIDCard.name(), familyIDCard.getName())
//            .put(familyNation.name(), familyNation.getName())
//            .put(familyPoliticalStatus.name(), familyPoliticalStatus.getName())
//            .put(familyHealthCondition.name(), familyHealthCondition.getName())
//            .put(familyCompanyOrganization.name(), familyCompanyOrganization.getName())
//            .put(familyPosition.name(), familyPosition.getName())
//            .build();

//    public static List<String> getValueByKeys(List<Object> keys){
//        return keys.stream().map(key -> String.valueOf(EXPORT_STUDENT_MAP.get(key)))
//                .collect(Collectors.toList());
//    }
}
