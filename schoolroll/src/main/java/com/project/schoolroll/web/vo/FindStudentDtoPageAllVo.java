package com.project.schoolroll.web.vo;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:31
 * @version: 1.0
 * @description:
 */
@Data
public class FindStudentDtoPageAllVo implements Serializable {
    /**
     * 学生id、学生代码
     */
    private String stuId;
    /**
     * 学生名称
     */
    private String stuName;
    /**
     * 学习中心id
     */
    private String centerId;
    /**
     * 学习类别
     */
    private String studentCategory;
    /**
     * 班级id
     */
    private String classId;
    /**
     * 专业id
     */
    private String specialtyId;
    /**
     * 学制
     */
    private String educationalSystem;
    /**
     * 就读方式/学习方式
     */
    private String waysStudy;
    /**
     * 学习形式
     */
    private String learningModality;
    /**
     * 入学方式
     */
    private String waysEnrollment;
    /**
     * 开始入学时间
     */
    private String enrollmentDateStartDate;
    /**
     * 开始入学结束时间
     */
    private String enrollmentDateEndDate;
    /**
     * 年级
     */
    private String grade;
    /**
     * 分页信息
     */
    private Pageable pageable;
}
