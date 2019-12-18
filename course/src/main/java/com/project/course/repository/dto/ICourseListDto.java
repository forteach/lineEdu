package com.project.course.repository.dto;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 09:49
 * @Version: 1.0
 * @Description:
 */

public interface ICourseListDto {

    /**
     * 课程ID
     *
     * @return
     */
    public String getCourseId();

    /**
     * 课程名称
     *
     * @return
     */
    public String getCourseName();

    /**
     * 别名
     * @return
     */
    public String getAlias();

    /**
     * 课程封面
     *
     * @return
     */
    public String getTopPicSrc();

    /**
     * 课程简介或描述
     * @return
     */
    public String getCourseDescribe();
    /** 有效，无效状态*/
    public String getIsValidated();
    /** 创建时间 */
    public String getCreateTime();
    /** 课程类型*/
    public Integer getCourseType();
}