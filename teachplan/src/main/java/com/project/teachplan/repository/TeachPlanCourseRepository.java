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
     *
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
            " c.course_type as courseType " +
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

    /**
     * 查询学习中心创建的计划对应的线上课程和混合课程
     *
     * @param centerAreaId
     * @return
     */
    @Transactional(readOnly = true)
    @Query(value = "select distinct courseId " +
            " from Course WHERE isValidated = '0' and courseType in (1, 3) and courseNumber in " +
            " (select distinct courseId from TeachPlanCourse where isValidated = '0' and centerAreaId = ?1 and type IN ('1','3')) ")
    List<String> findAllByCenterAreaId(String centerAreaId);

//    @Transactional(readOnly = true)
//    @Query(value = "select " +
//            " courseId as courseId, courseName as courseName, courseDescribe as courseDescribe," +
//            " courseType as courseType, courseNumber as courseNumber, alias as alias, " +
//            " topPicSrc as topPicSrc, learningTime as learningTime, videoPercentage as videoPercentage, " +
//            " videoTimeNum as videoTimeNum, isValidated as isValidated, createTime as createTime, updateTime as updateTime " +
//            " from Course WHERE isValidated = '0' and courseType in (1, 3) and courseNumber in " +
//            " (select distinct courseId from TeachPlanCourse where isValidated = '0' and centerAreaId = ?1 and type IN ('1','3')) ")
//    List<Course> findAllByCenterAreaId(String centerAreaId);

//    /**
//     * 根据计划负责人Id 查询课程信息
//     */
//    @Transactional(readOnly = true)
//    @Query(value = "select " +
//            " courseId as courseId, courseName as courseName, courseDescribe as courseDescribe," +
//            " courseType as courseType, courseNumber as courseNumber, alias as alias, jobsPercentage as jobsPercentage, " +
//            " topPicSrc as topPicSrc, learningTime as learningTime, videoPercentage as videoPercentage, " +
//            " videoTimeNum as videoTimeNum, isValidated as isValidated, createTime as createTime, updateTime as updateTime " +
//            " from Course WHERE isValidated = '0' and courseType in (1, 3) and courseNumber IN " +
//            " (select distinct courseId from TeachPlanCourse where isValidated = '0' and type IN ('1', '3') and planId IN " +
//            " (select distinct planId from TeachPlan where isValidated = '0' and planAdminId = ?1)) ")
//    List<Course> findAllByPlanAdminId(String planAdminId);


    @Transactional(readOnly = true)
    @Query(value = "select distinct courseId " +
            " from Course WHERE isValidated = '0' and courseType in (1, 3) and courseNumber IN " +
            " (select distinct courseId from TeachPlanCourse where isValidated = '0' and type IN ('1', '3') and planId IN " +
            " (select distinct planId from TeachPlan where isValidated = '0' and planAdminId = ?1)) ")
    List<String> findAllByPlanAdminId(String planAdminId);
}