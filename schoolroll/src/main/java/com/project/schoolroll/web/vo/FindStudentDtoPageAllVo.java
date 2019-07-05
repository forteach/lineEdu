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
    private String stuId;
    private String stuName;
    private String centerId;
    private String studentCategory;
    private String classId;
    private String specialtyId;
    private String educationalSystem;
    private String waysStudy;
    private String learningModality;
    private String waysEnrollment;
    private String enrollmentDateStartDate;
    private String enrollmentDateEndDate;
    private String grade;
    private Pageable pageable;
}
