package com.project.schoolroll.repository.online;

import com.project.schoolroll.domain.online.OnLineCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 14:32
 * @Version: 1.0
 * @Description:
 */
public interface OnLineCourseRepository extends JpaRepository<OnLineCourse, String> {

    @Transactional(readOnly = true)
    Optional<OnLineCourse> findAllByCourseName(String courseName);

    @Transactional(readOnly = true)
    List<OnLineCourse> findAllByIsValidatedEqualsAndCenterAreaId(String isValidated, String centerAreaId);

    @Transactional(readOnly = true)
    List<OnLineCourse> findAllByIsValidatedEquals(String isValidated);

    @Transactional(readOnly = true)
    Page<OnLineCourse> findAllByIsValidatedEqualsAndCenterAreaId(String isValidated, String centerAreaId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<OnLineCourse> findAllByIsValidatedEquals(String isValidated, Pageable pageable);

}