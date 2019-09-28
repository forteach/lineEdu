package com.project.teachplan.repository;

import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.repository.dto.CourseTeacherDto;
import com.project.teachplan.repository.dto.PlanCourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Modifying(clearAutomatically = true)
    int deleteAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<TeachPlanCourse> findAllByPlanId(String planId);

    @Transactional(readOnly = true)
    Optional<TeachPlanCourse> findByIsValidatedEqualsAndPlanIdAndCourseId(String isValidated, String planId, String courseId);

    @Query(value = "select distinct courseId from TeachPlanCourse where isValidated = '0'" +
            " and planId in (select distinct planId from TeachPlanClass where isValidated = '0' and classId = ?1)")
    @Transactional(readOnly = true)
    List<String> findAllByIsValidatedEqualsAndClassId(String classId);

    @Query(value = "select courseId as courseId, teacherId as teacherId from TeachPlanCourse where isValidated = '0'" +
            " and planId in (select distinct planId from TeachPlanClass where isValidated = '0' and classId = ?1) " +
            " order by createTime desc ")
    @Transactional(readOnly = true)
    List<CourseTeacherDto> findAllByIsValidatedEqualsAndClassIdDto(String classId);
}