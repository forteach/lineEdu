package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlanClass;
import com.project.teachplan.repository.dto.TeachPlanClassDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeachPlanClassRepository extends JpaRepository<TeachPlanClass, String> {
    @Modifying(flushAutomatically = true)
    int deleteAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<TeachPlanClass> findAllByPlanId(String planId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TeachPlanClass set isValidated = ?1 where planId = ?2")
    int updateIsValidatedByPlanId(String isValidated, String planId);

    @Transactional(readOnly = true)
    List<TeachPlanClass> findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(String isValidated, String planId);

    @Query(value = " select " +
            " tpc.classId as classId," +
            " tpc.className as className," +
            " tp.planId as planId," +
            " tp.planName as planName," +
            " tp.planAdmin as planAdmin," +
            " tp.startDate as startDate," +
            " tp.endDate as endDate, " +
            " tp.createTime as createTime " +
            " from TeachPlanClass AS tpc left join TeachPlan as tp " +
            " on tpc.planId = tp.planId " +
            " where tpc.isValidated = '0' and tp.isValidated = '0' and tpc.centerAreaId = ?1")
    @Transactional(readOnly = true)
    Page<TeachPlanClassDto> findAllByCenterAreaIdDto(String centerAreaId, Pageable pageable);

    @Query(value = " select " +
            " tpc.classId as classId," +
            " tpc.className as className," +
            " tp.planId as planId," +
            " tp.planName as planName," +
            " tp.planAdmin as planAdmin," +
            " tp.startDate as startDate," +
            " tp.endDate as endDate," +
            " tp.createTime as createTime " +
            " from TeachPlanClass AS tpc left join TeachPlan as tp " +
            " on tpc.planId = tp.planId " +
            " where tpc.isValidated = '0' and tp.isValidated = '0' and tpc.centerAreaId = ?1 and tpc.classId = ?2")
    @Transactional(readOnly = true)
    Page<TeachPlanClassDto> findAllByCenterAreaIdAndClassIdDto(String centerAreaId, String classId, Pageable pageable);
}