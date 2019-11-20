package com.project.teachplan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 15:44
 * @version: 1.0
 * @description:
 */
@Data
public abstract class BaseTeachCourseVo implements Serializable {
    /**
     * 学生Id
     */
    public String studentId;

    /**
     * 学生名称
     */
    public String studentName;

    /**
     * 电话
     */
    public String stuPhone;

    /**
     * 学习中心Id
     */
    public String centerAreaId;

    /**
     * 学习中心Id
     */
    public String centerName;

    /**
     * 计划Id
     */
    public String planId;

    /**
     * 计划名称planeName
     */
    public String planName;

    /**
     * 开始时间
     */
    public String startDate;

    /**
     * 结束时间
     */
    public String endDate;

    public BaseTeachCourseVo(String studentId, String studentName, String stuPhone,
                             String centerAreaId, String centerName, String planId, String planName, String startDate, String endDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.stuPhone = stuPhone;
        this.centerAreaId = centerAreaId;
        this.centerName = centerName;
        this.planId = planId;
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
