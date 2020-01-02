package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentFinishSchool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/2 16:14
 * @version: 1.0
 * @description:
 */
public interface StudentFinishSchoolRepository extends JpaRepository<StudentFinishSchool, String> {

    List<StudentFinishSchool> findByIsValidatedEqualsAndIsFinishSchool(String isValidated, String isFinishSchool);

    Page<StudentFinishSchool> findAllByIsValidatedEqualsAndIsFinishSchool(String isValidated, String isFinishSchool, Pageable pageable);

    Page<StudentFinishSchool> findAllByIsValidatedEquals(String isValidated, Pageable pageable);

    Page<StudentFinishSchool> findAllByIsFinishSchool(String isFinishSchool, Pageable pageable);
}