package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:29
 * @version: 1.0
 * @description:
 */
public interface StudentScoreRepository extends JpaRepository<StudentScore, String> {

    @Transactional(readOnly = true)
    public StudentScore findAllByIsValidatedEqualsAndStuIdAndCourseId(String isValidated, String stuId, String courseId);

    @Transactional(readOnly = true)
    public List<StudentScore> findAllByIsValidatedEqualsAndStuIdOrderByUpdateTime(String isValidated, String stuId);
}
