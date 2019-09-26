package com.project.user.repository;

import com.project.user.domain.TeacherFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-24 13:58
 * @version: 1.0
 * @description:
 */
public interface TeacherFileRepository extends JpaRepository<TeacherFile, String> {
    @Transactional(readOnly = true)
    List<TeacherFile> findAllByIsValidatedEqualsAndTeacherId(String isValidated, String teacherId);

    @Transactional(readOnly = true)
    List<TeacherFile> findAllTeacherId(String teacherId);

}