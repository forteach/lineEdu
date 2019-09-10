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

    /**
     * 所有的文件列表
     */
    public Page<PlanFile> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerId, Pageable pageable);

    /**
     * 班级所有的文件列表
     */
    public Page<PlanFile> findAllByIsValidatedEqualsAndCenterAreaIdAndClassIdOrderByCreateTimeDesc(String isValidated, String centerId, String classId, Pageable pageable);

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
    public List<PlanFile> findAllByIsValidatedEqualsAndClassId(String isValidated, String classId);

    @Transactional(readOnly = true)
    public List<PlanFile> findAllByIsValidatedEqualsAndPlanId(String isValidated, String planId);
}