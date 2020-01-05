package com.project.schoolroll.service.impl;

import com.project.schoolroll.domain.StudentFinishSchool;
import com.project.schoolroll.repository.StudentFinishSchoolRepository;
import com.project.schoolroll.service.StudentFinishSchoolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/2 16:19
 * @version: 1.0
 * @description:
 */
@Service
public class StudentFinishSchoolServiceImpl implements StudentFinishSchoolService {
    private final StudentFinishSchoolRepository studentFinishSchoolRepository;

    public StudentFinishSchoolServiceImpl(StudentFinishSchoolRepository studentFinishSchoolRepository) {
        this.studentFinishSchoolRepository = studentFinishSchoolRepository;
    }

    @Override
    public Page<StudentFinishSchool> findAllByIsValidatedEqualsAndIsFinishSchoolPage(String isValidated, String isFinishSchool, Pageable pageable) {
        return studentFinishSchoolRepository.findAllByIsValidatedEqualsAndIsFinishSchool(isValidated, isFinishSchool, pageable);
    }

    @Override
    public Page<StudentFinishSchool> findAllByIsValidatedEqualsPage(String isValidated, Pageable pageable) {
        return studentFinishSchoolRepository.findAllByIsValidatedEquals(isValidated, pageable);
    }

    @Override
    public Page<StudentFinishSchool> findAllIsFinishSchoolPage(String isFinishSchool, Pageable pageable) {
        return studentFinishSchoolRepository.findAllByIsFinishSchool(isFinishSchool, pageable);
    }

    @Override
    public StudentFinishSchool findById(String studentId) {
        return studentFinishSchoolRepository.findById(studentId).orElseGet(StudentFinishSchool::new);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<StudentFinishSchool> list) {
        studentFinishSchoolRepository.saveAll(list);
    }
}