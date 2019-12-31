package com.project.teachplan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 15:46
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseScoreVo extends BaseTeachCourseVo{

    public List<ScoreVo> courses;

    public CourseScoreVo(String studentId, String stuId, String specialtyName, String grade, String classId, String className, String studentName, String stuPhone, String centerAreaId, String centerName, String planId, String planName, String startDate, String endDate, List<ScoreVo> courses) {
        super(studentId, stuId, specialtyName, grade, classId, className, studentName, stuPhone, centerAreaId, centerName, planId, planName, startDate, endDate);
        this.courses = courses;
    }
}