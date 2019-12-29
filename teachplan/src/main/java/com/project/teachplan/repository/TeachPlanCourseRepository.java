package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlanCourse;
import com.project.teachplan.repository.dto.CourseTeacherDto;
import com.project.teachplan.repository.dto.IPlanCourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课时费管理
 */
public interface TeachPlanCourseRepository extends JpaRepository<TeachPlanCourse, String>, JpaSpecificationExecutor<TeachPlanCourse> {

    @Transactional(readOnly = true)
    List<TeachPlanCourse> findAllByIsValidatedEqualsAndPlanId(String isValidated, String planId);

    @Modifying(clearAutomatically = true)
    int deleteAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<TeachPlanCourse> findAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<CourseTeacherDto> findAllByIsValidatedEqualsAndPlanIdIn(String isValidated, List<String> planIds);

    /**
     * 查讯计划对应的课程计划成绩占比
     * @param planId
     * @return
     */
    @Query(value = " SELECT " +
            " c.course_id as courseId," +
            " c.course_name as courseName, " +
            " tpcv.line_percentage as linePercentage, " +
            " tpcv.on_line_percentage as onLinePercentage, " +
            " c.video_percentage as videoPercentage, " +
            " c.jobs_percentage as jobsPercentage," +
            " c.courseType as courseType " +
            " from (select line_percentage, on_line_percentage, course_id from teach_plan_course where is_validated = '0' and plan_id = ?1) as tpcv " +
            " left join course as c on c.course_number = tpcv.course_id ", nativeQuery = true)
    @Transactional(readOnly = true)
    List<IPlanCourseDto> findAllPlanCourseDtoByPlanId(String planId);

    @Modifying
    void deleteAllByCourseId(String courseId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update TeachPlanCourse set isValidated = ?1 where planId = ?2")
    int updateIsValidatedByPlanId(String isValidated, String planId);

    @Query(value = "select distinct courseId from TeachPlanCourse where isValidated = '0' and planId in (select planId from TeachPlan where isValidated = '0' and classId =?1)")
    Page<String> findAllByClassId(String classId, Pageable pageable);

    @Query(value = "select distinct courseId from TeachPlanCourse where isValidated = '0' and planId in (select planId from TeachPlan where isValidated = '0' and classId =?1)")
    List<String> findAllByClassId(String classId);

    @Query(value = "select distinct courseId from TeachPlanCourse where isValidated = '0' and type  = ?1 and planId in (select planId from TeachPlan where isValidated = '0' and classId = ?2)")
    List<String> findAllByTypeAndClassId(String type, String classId);
}