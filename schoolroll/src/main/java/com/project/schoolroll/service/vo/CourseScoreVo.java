package com.project.schoolroll.service.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/12/29 19:47
 * @Version: 1.0
 * @Description:
 */
@Data
public class CourseScoreVo {
    public String studentId;
    public String studentName;
    public String stuId;
    public String gender;
    public String grade;
    public String specialtyName;
    public List<Map<String, String>> courseScore;

    public CourseScoreVo() {
    }

    public CourseScoreVo(String studentId, String studentName, String stuId, String gender,
                         String grade, String specialtyName, List<Map<String, String>> courseScore) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.stuId = stuId;
        this.gender = gender;
        this.grade = grade;
        this.specialtyName = specialtyName;
        this.courseScore = courseScore;
    }
}