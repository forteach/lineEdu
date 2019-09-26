package com.project.teachplan.repository;

import com.project.teachplan.domain.PlanFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 培训项目班级资料管理
 */
public interface PlanFileRepository extends JpaRepository<PlanFile, String>, JpaSpecificationExecutor<PlanFile> {

    @Transactional(readOnly = true)
    Page<PlanFile> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    /**
     * 根据计划编号查询对应的班级资料分页信息
     *
     * @param planId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    Page<PlanFile> findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(String isValidated, String planId, Pageable pageable);

    @Transactional(readOnly = true)
    List<PlanFile> findAllByIsValidatedEqualsAndPlanIdAndClassIdAndCourseIdAndCreateDateOrderByCreateTimeDesc(String isValidated, String planId, String classId, String courseId, String createDate);



    @Transactional(readOnly = true)
    List<PlanFile> findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateTimeDesc(String isValidated, String planId, String classId);


    @Transactional(readOnly = true)
    List<PlanFile> findAllByPlanId(String planId);

    @Query(value = "select * from plan_file where is_validated = '0' and DATE(c_time) = ?1 ", nativeQuery = true)
    @Transactional(readOnly = true)
    List<PlanFile> findAllByIsValidatedEqualsAndCreateTime(String dateStr);

    List<PlanFile> findAllByPlanIdAndClassIdAndCourseIdAndCreateDate(String planId, String classId, String courseId, String createDate);

//    @Query(value = "select " +
//            " pf.file_id as fileId," +
//            " pf.file_name as fileName," +
//            " pf.file_url as fileUrl," +
//            " tpc.class_id as classId," +
//            " tpc.class_name as className," +
//            " tp.plan_id as planId, " +
//            " tp.plan_name as planName, " +
//            " tp.start_date as startDate, " +
//            " tp.end_date as endDate, " +
//            " tp.plan_admin as planAdmin " +
//            " from plan_file as pf " +
//            " left join teach_plan as tp on tp.plan_id = pf.plan_id " +
//            " left join teach_plan_class as tpc on tpc.plan_id = pf.plan_id " +
//            " where pf.is_validated = '0' and tp.is_validated = '0' and tpc.is_validated = '0' " +
//            " and order by ?#{#pageable}",
//            countQuery = " select count (1) form plan_file " +
//                    " where is_validated = '0' order by ?#{#pageable}", nativeQuery = true)
//    @Transactional(readOnly = true)
//    Page<PlanFileDto> findAllByIsValidatedEqualsPageAll(Pageable pageable);
}