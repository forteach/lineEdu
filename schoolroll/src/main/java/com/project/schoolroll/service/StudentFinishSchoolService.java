package com.project.schoolroll.service;

import com.project.schoolroll.domain.StudentFinishSchool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/2 16:18
 * @version: 1.0
 * @description:
 */
public interface StudentFinishSchoolService {
    Page<StudentFinishSchool> findAllByIsValidatedEqualsAndIsFinishSchoolPage(String isValidated, String isFinishSchool, Pageable pageable);
    Page<StudentFinishSchool> findAllByIsValidatedEqualsPage(String isValidated, Pageable pageable);
    Page<StudentFinishSchool> findAllIsFinishSchoolPage(String isFinishSchool, Pageable pageable);
    public StudentFinishSchool findById(String studentId);
}