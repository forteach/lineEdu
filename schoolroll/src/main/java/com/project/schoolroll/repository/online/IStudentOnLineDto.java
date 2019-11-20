package com.project.schoolroll.repository.online;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 17:12
 * @version: 1.0
 * @description:
 */
public interface IStudentOnLineDto {
    public String getStudentId();

    public String getStuId();

    /**
     * 姓名
     */
    public String getStudentName();

    /**
     * 性别
     * 男/女
     */
    public String getGender();

    /**
     * 身份证号
     */
    public String getStuIDCard();

    /**
     * 联系电话
     */
    public String getStuPhone();

    /**
     * 班级id
     */
    public String getClassId();

    /**
     * 班级名称
     */
    public String getClassName();

    /**
     * 入学时间(年/月)
     */
    public String getEnrollmentDate();

    /**
     * 民族
     * 汉族
     * 满族
     * 其他
     */
    public String getNation();

    /**
     * 学习形式全日制非全日制
     */
    public String getLearningModality();

    /**
     * 学生信息 0 表格导入, 1 手动添加
     */
    public Integer getImportStatus();

    public String getCenterAreaId();

    public String getCenterName();

    public String getCreateTime();

    public String getIsValidated();
}
