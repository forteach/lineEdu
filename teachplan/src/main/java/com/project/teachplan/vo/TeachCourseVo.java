package com.project.teachplan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/11/5 18:47
 * @Version: 1.0
 * @Description:
 */
@Data
public class TeachCourseVo implements Serializable {
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

    public Map<String, String> course;

    public TeachCourseVo() {
    }

    public TeachCourseVo(String studentId, String studentName, String stuPhone,
                         String centerAreaId, String centerName, String planId,
                         String planName, String startDate, String endDate) {
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
