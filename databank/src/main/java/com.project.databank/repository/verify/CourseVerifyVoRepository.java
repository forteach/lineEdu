package com.project.databank.repository.verify;

import com.project.databank.domain.verify.CourseVerifyVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:35
 * @version: 1.0
 * @description:
 */
public interface CourseVerifyVoRepository extends JpaRepository<CourseVerifyVo, String> {
    @Transactional(readOnly = true)
    Page<CourseVerifyVo> findAllByIsValidatedEqualsAndCourseId(String isValidated, String courseId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<CourseVerifyVo> findAllByIsValidatedEqualsAndVerifyStatus(String isValidated, String verifyStatus, Pageable pageable);

    @Transactional(readOnly = true)
    Page<CourseVerifyVo> findAllByIsValidatedEqualsAndVerifyStatusAndCourseId(String isValidated, String verifyStatus, String courseId, Pageable pageable);
}
