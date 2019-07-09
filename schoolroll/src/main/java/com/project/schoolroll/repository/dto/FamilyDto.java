package com.project.schoolroll.repository.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:42
 * @version: 1.0
 * @description:
 */
public interface FamilyDto {
    /**
     * 家庭id
     * @return
     */
    public String getFamilyId();

    /**
     * 学生id
     * @return
     */
    public String getStuId();

    /**
     * 姓名
     * @return
     */
    public String getName();

    /**
     * 电话
     * @return
     */
    public String getPhone();

    /**
     * 家庭关系
     * @return
     */
    public String getFamilyRelationship();

    /**
     * 是否是监护人
     * @return
     */
    public String getIsGuardian();

}
