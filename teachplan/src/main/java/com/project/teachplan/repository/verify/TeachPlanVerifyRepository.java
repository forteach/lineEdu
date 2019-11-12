package com.project.teachplan.repository.verify;

import com.project.teachplan.domain.verify.TeachPlanVerify;
import com.project.teachplan.repository.dto.TeachPlanDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 18:04
 * @version: 1.0
 * @description:
 */
public interface TeachPlanVerifyRepository extends JpaRepository<TeachPlanVerify, String> {

    @Transactional(readOnly = true)
    List<TeachPlanVerify> findAllByVerifyStatusEqualsAndPlanId(String verifyStatus, String planId);

    @Transactional(readOnly = true)
    List<TeachPlanVerify> findAllByPlanId(String planId);

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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus," +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.verifyStatus =?1 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByVerifyStatusDto(String verifyStatus, Pageable pageable);

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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.verifyStatus =?1 and tp.centerAreaId = ?2 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByVerifyStatusAndCenterAreaIdDto(String verifyStatus, String centerAreaId, Pageable pageable);

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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
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
            " lc.centerName as centerName, " +
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.centerAreaId = ?1 and tp.planId = ?2 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByCenterAreaIdAndPlanIdDto(String centerAreaId, String planId, Pageable pageable);

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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.planId = ?1 and tp.verifyStatus = ?2 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByPlanIdAndVerifyStatusDto(String planId, String verifyStatus, Pageable pageable);

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
            " tp.isValidated as isValidated, " +
            " tp.createTime as createTime," +
            " tp.verifyStatus as verifyStatus, " +
            " tp.remark as remark " +
            " from TeachPlanVerify as tp " +
            " left join LearnCenter as lc on lc.centerId = tp.centerAreaId " +
            " where tp.planId = ?1 and tp.verifyStatus = ?2 and tp.centerAreaId = ?3 order by tp.createTime desc ")
    @Transactional(readOnly = true)
    Page<TeachPlanDto> findAllPageByPlanIdAndCenterAreaIdDto(String planId, String verifyStatus, String centerAreaId, Pageable pageable);
}