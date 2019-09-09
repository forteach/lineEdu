package com.project.user.repository;


import com.project.user.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 10:45
 * @version: 1.0
 * @description:
 */
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    @Transactional(readOnly = true)
    Page<Teacher> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    @Transactional(readOnly = true)
    List<Teacher> findAllByIsValidatedEquals(String isValidated);

    @Transactional(readOnly = true)
    List<Teacher> findAllByIsValidatedEqualsAndTeacherCode(String isValidated, String teacherCode);

    @Transactional(readOnly = true)
    List<Teacher> findAllByIsValidatedEqualsAndTeacherName(String isValidated, String teacherName);

    @Modifying(clearAutomatically = true)
    int deleteTeacherByTeacherCode(String teacherCode);

}