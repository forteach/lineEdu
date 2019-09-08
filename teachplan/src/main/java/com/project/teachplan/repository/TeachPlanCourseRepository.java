package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlanCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 课时费管理
 */
public interface TeachPlanCourseRepository extends JpaRepository<TeachPlanCourse, String>, JpaSpecificationExecutor<TeachPlanCourse> {

    @Transactional(readOnly = true)
    List<TeachPlanCourse> findAllByIsValidatedEqualsAndPlanId(String isValidated, String planId);

    @Transactional(readOnly = true)
    List<TeachPlanCourse> findAllByIsValidatedEqualsAndCourseId(String isValidated, String courseId);

    @Modifying(flushAutomatically = true)
    int deleteAllByPlanId(String planId);

    @Transactional(readOnly = true)
    Optional<TeachPlanCourse> findByIsValidatedEqualsAndPlanIdAndCourseId(String isValidated, String planId, String courseId);
}
