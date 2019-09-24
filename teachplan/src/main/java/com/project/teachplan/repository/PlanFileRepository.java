package com.project.teachplan.repository;

import com.project.teachplan.domain.PlanFile;
import com.project.teachplan.repository.dto.PlanFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 培训项目班级资料管理
 */
public interface PlanFileRepository extends JpaRepository<PlanFile, String>, JpaSpecificationExecutor<PlanFile> {

    /**
     * 所有的文件列表
     */
    public Page<PlanFile> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerId, Pageable pageable);

    public Page<PlanFile> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    /**
     * 班级所有的文件列表
     */
    public Page<PlanFile> findAllByIsValidatedEqualsAndCenterAreaIdAndClassIdOrderByCreateTimeDesc(String isValidated, String centerId, String classId, Pageable pageable);

    public Page<PlanFile> findAllByIsValidatedEqualsAndClassIdOrderByCreateTimeDesc(String isValidated, String classId, Pageable pageable);

    /**
     * 根据计划编号查询对应的班级资料分页信息
     *
     * @param planId
     * @param pageable
     * @return
     */
    public Page<PlanFile> findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(String isValidated, String planId, Pageable pageable);

    /**
     * 获得计划下面的班级数量
     */
    @Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT class_id from plan_file WHERE is_validated = '0' AND plan_id=?1) as t", nativeQuery = true)
    public int countClass(String planId);

    @Transactional(readOnly = true)
    public List<PlanFile> findAllByIsValidatedEqualsAndClassIdOrderByCreateTimeDesc(String isValidated, String classId);

    @Transactional(readOnly = true)
    public List<PlanFile> findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(String isValidated, String planId);

    @Transactional(readOnly = true)
    List<PlanFile> findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateTimeDesc(String isValidated, String planId, String classId);

    @Modifying(flushAutomatically = true)
    long deleteAllByFileIdIn(List<String> ids);


    @Query(value = "select " +
            " fileId AS fileId, " +
            " fileName AS fileName," +
            " fileUrl AS fileUrl," +
            " classId AS classId," +
            " planId AS planId " +
            " from PlanFile where isValidated = '0' and " +
            " planId in (select distinct planId from TeachPlanCourse where isValidated = '0' and courseId = ?1)")
    @Transactional(readOnly = true)
    List<PlanFileDto> findAllByIsValidatedEqualsAndCourseIdDto(String courseId);

//    List<PlanFile> findAllByIsValidatedEqualsAndC

//    public Page<PlanFile> findAllByIsValidatedEqualsAndPlanIdAndCourseIdOrderByCreateTimeDesc(String isValidated, String planId, String courseId, Pageable pageable);

//    public Page<PlanFile> findAllByIsValidatedEqualsAndPlanIdAndClassIdOrderByCreateTimeDesc(String isValidated, String planId, String classId, Pageable pageable);

    // FileId FileName FileUrl ClassId ClassName PlanId PlanName StartDate EndDate PlanAdmin

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