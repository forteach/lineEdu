package com.project.teachplan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/11/5 18:47
 * @Version: 1.0
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeachCourseVo extends BaseTeachCourseVo {

    public List<StudyVo> courses;

    public TeachCourseVo(String studentId, String studentName, String stuPhone, String centerAreaId, String centerName, String planId, String planName,
                         String startDate, String endDate, List<StudyVo> courses) {
        super(studentId, studentName, stuPhone, centerAreaId, centerName, planId, planName, startDate, endDate);
        this.courses = courses;
    }
}
