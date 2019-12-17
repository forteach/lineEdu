package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlan;
import com.project.teachplan.repository.dto.IPlanStatusDto;
import com.project.teachplan.repository.dto.PlanCourseStudyDto;
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
public interface TeachPlanRepository extends JpaRepository<TeachPlan, String>, JpaSpecificationExecutor<TeachPlan> {

    @Modifying
    void deleteAllByPlanId(String planId);

//    @Query(value = "select planId AS planId, status as status, countStatus as countStatus from TeachPlan where isValidated = '0' " +
//            " and startDate <= ?1 and endDate >= ?1 and planId in (select planId from TeachPlanClass where isValidated = '0' and classId = ?2)")
//    @Transactional(readOnly = true)
//    List<IPlanStatusDto> findAllByClassId(String nowDate, String classId);

//    @Query(value = "select planId AS planId, status as status, countStatus as countStatus from TeachPlan where isValidated = '0' " +
//            " and startDate <= ?1 and endDate >= ?1 and planId in (select planId from TeachPlanClass where isValidated = '0' and classId = ?2)")
//    @Transactional(readOnly = true)
//    List<IPlanStatusDto> findAllByClassId(String nowDate, String classId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update TeachPlan set isValidated = ?1 where planId = ?2")
    int updateIsValidatedByPlanId(String isValidated, String planId);

    @Transactional(readOnly = true)
    List<TeachPlan> findAllByStatusAndEndDateBefore(int status, String endDate);

    @Transactional(readOnly = true)
    List<TeachPlan> findAllByIsValidatedEqualsAndStatusAndCountStatus(String isValidated, int status, int countStatus);

    /**
     * 根据计划Id分页查询学生和课程信息
     */
    @Query(value = " select " +
            " tp.plan_id as planId," +
            " tp.plan_name as planName, " +
            " tp.start_date as startDate, " +
            " tp.end_date as endDate, " +
            " s_lc_v.center_name as centerName, " +
            " s_lc_v.center_area_id as centerAreaId, " +
            " s_lc_v.student_id as studentId, " +
            " s_lc_v.student_name as studentName, " +
            " s_lc_v.stu_phone as stuPhone, " +
            " GROUP_CONCAT(concat(tpc.course_name, '&', tpc.course_id, '&', tpc.teacher_Id)) as course " +
            " from ( SELECT " +
            " s.student_id, " +
            " s.stu_phone, " +
            " s.student_name, " +
            " s.center_area_id, " +
            " lc.center_name, " +
            " tpc.plan_id  from student_on_line as s " +
            " inner join learn_center as lc on lc.center_id = s.center_area_id " +
            " LEFT JOIN teach_plan_class as tpc on tpc.center_area_id = s.center_area_id " +
            " where s.is_validated = '0' and tpc.plan_id = ?1 " +
            " and s.class_id in  (select DISTINCT class_id from teach_plan_class  where is_validated = '0' and plan_id = ?1) " +
            " GROUP BY s.student_id) as s_lc_v " +
            " LEFT JOIN teach_plan as tp on tp.plan_id = s_lc_v.plan_id " +
            " LEFT JOIN teach_plan_course as tpc on tpc.plan_id = s_lc_v.plan_id " +
            " where tp.is_validated = '0' and tp.plan_id = ?1 GROUP BY student_id ",
            countQuery = " select count(*) from( " +
                    " select " +
                    " count(*) " +
                    " from(SELECT " +
                    " s.student_id, " +
                    " s.stu_phone, " +
                    " s.student_name, " +
                    " s.center_area_id, " +
                    " lc.center_name, " +
                    " tpc.plan_id  from student_on_line as s " +
                    " inner join learn_center as lc on lc.center_id = s.center_area_id " +
                    " LEFT JOIN teach_plan_class as tpc on tpc.center_area_id = s.center_area_id " +
                    " where s.is_validated = '0' and tpc.plan_id = ?1 " +
                    " and s.class_id in  (select DISTINCT class_id from teach_plan_class  where is_validated = '0' and plan_id = ?1) " +
                    " GROUP BY s.student_id) as s_lc_v " +
                    " LEFT JOIN teach_plan as tp on tp.plan_id = s_lc_v.plan_id " +
                    " LEFT JOIN teach_plan_course as tpc on tpc.plan_id = s_lc_v.plan_id " +
                    " where tp.is_validated = '0' and tp.plan_id = ?1 GROUP BY student_id) as v ",
            nativeQuery = true)
    @Transactional(readOnly = true)
    Page<PlanCourseStudyDto> findAllPageDtoByPlanId(String planId, Pageable pageable);
}