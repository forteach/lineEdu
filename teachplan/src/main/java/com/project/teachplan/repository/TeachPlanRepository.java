package com.project.teachplan.repository;

import com.project.teachplan.domain.online.TeachPlan;
import com.project.teachplan.repository.dto.TeachPlanDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课时费管理
 */
public interface TeachPlanRepository extends JpaRepository<TeachPlan, String>, JpaSpecificationExecutor<TeachPlan> {

    @Transactional(readOnly = true)
    Page<TeachPlan> findAllByIsValidatedEqualsAndCenterAreaIdAndPlanIdOrderByCreateTimeDesc(String isValidated, String centerAreaId, String planId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<TeachPlan> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerAreaId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "update TeachPlan set isValidated = ?1 where planId = ?2")
    int updateIsValidatedByPlanId(String isValidated, String planId);

    @Transactional
    @Modifying(clearAutomatically = true)
    int deleteByPlanId(String planId);

    @Query(value = "select " +
            " tp.planId as planId, " +
            " tp.planName as planName," +
            " tp.startDate as startDate," +
            " tp.endDate as endDate," +
            " tp.planAdmin as planAdmin," +
            " tp.courseNumber as courseNumber, " +
            " tp.classNumber as classNumber, " +
            " tp.sumNumber as sumNumber, " +
            " tp.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName, " +
            " tp.isValidated as isValidated " +
            " from TeachPlan as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageDto(Pageable pageable);

    @Query(value = "select " +
            " tp.planId as planId, " +
            " tp.planName as planName," +
            " tp.startDate as startDate," +
            " tp.endDate as endDate," +
            " tp.planAdmin as planAdmin," +
            " tp.courseNumber as courseNumber, " +
            " tp.classNumber as classNumber, " +
            " tp.sumNumber as sumNumber, " +
            " tp.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName, " +
            " tp.isValidated as isValidated " +
            " from TeachPlan as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.planId = ?1 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByPlanIdDto(String planId, Pageable pageable);

    @Query(value = "select " +
            " tp.planId as planId, " +
            " tp.planName as planName," +
            " tp.startDate as startDate," +
            " tp.endDate as endDate," +
            " tp.planAdmin as planAdmin," +
            " tp.courseNumber as courseNumber, " +
            " tp.classNumber as classNumber, " +
            " tp.sumNumber as sumNumber, " +
            " tp.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName, " +
            " tp.isValidated as isValidated " +
            " from TeachPlan as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.centerAreaId = ?1 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByCenterAreaIdDto(String centerAreaId, Pageable pageable);

    @Query(value = "select " +
            " tp.planId as planId, " +
            " tp.planName as planName," +
            " tp.startDate as startDate," +
            " tp.endDate as endDate," +
            " tp.planAdmin as planAdmin," +
            " tp.courseNumber as courseNumber, " +
            " tp.classNumber as classNumber, " +
            " tp.sumNumber as sumNumber, " +
            " tp.centerAreaId as centerAreaId, " +
            " lc.centerName as centerName," +
            " tp.isValidated as isValidated " +
            " from TeachPlan as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.centerAreaId = ?1 and tp.planId = ?2 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByCenterAreaIdAndPlanIdDto(String centerAreaId, String planId, Pageable pageable);
}