package com.project.schoolroll.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:06
 * @version: 1.0
 * @description:
 */
public interface StudentDto {

    /**
     * 学生id
     * @return
     */
    public String getStuId();

    /**
     * 学生名称
     * @return
     */
    public String getStuName();

    /**
     * 专业id
     * @return
     */
    public String getSpecialtyId();

    /**
     * 专业名称简称
     * @return
     */
    public String getSpecialtyName();

    /**
     * 个人id，对应studentPeople的id
     * @return
     */
    public String getPeopleId();

    /**
     * 学习中心id
     * @return
     */
    public String getCenterId();

    /**
     * 学习中心名称
     * @return
     */
    public String getCenterName();

    /**
     * 学生类别
     * @return
     */
    public String getStudentCategory();

    /**
     * 班级id
     * @return
     */
    public String getClassId();

    /**
     * 班级名称
     * @return
     */
    public String getClassName();

    /**
     * 学制
     * 三年制，四年制，五年制，一年制
     * @return
     */
    public String getEducationalSystem();

    /**
     * 就读方式/学习方式
     * @return
     */
    public String getWaysStudy();

    /**
     * 学习形式
     * 全日制 非全日制
     * @return
     */
    public String getLearningModality();

    /**
     * 入学方式
     * @return
     */
    public String getWaysEnrollment();

    /**
     * 入学时间(年/月)
     * @return
     */
    public String getEnrollmentDate();

    /**
     * 年级
     * @return
     */
    public String getGrade();

    /**
     * 准考证号码
     * @return
     */
    public String getEntranceCertificateNumber();

    /**
     * 考生号
     * @return
     */
    public String getCandidateNumber();

    /**
     * 考试总成绩
     * @return
     */
    public String getTotalExaminationAchievement();

}
