package com.project.teachplan.repository;

import com.project.teachplan.domain.online.TeachPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课时费管理
 */
public interface TeachPlanRepository extends JpaRepository<TeachPlan, String>, JpaSpecificationExecutor<TeachPlan> {

    @Transactional(readOnly = true)
    Page<TeachPlan> findByIsValidatedEqualsAndPlanId(String isValidated, String planId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<TeachPlan> findByIsValidatedEquals(String isValidated, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "update TeachPlan set isValidated = ?1 where planId = ?2")
    int updateIsValidatedByPlanId(String isValidated, String planId);

    @Transactional
    @Modifying(clearAutomatically = true)
    int deleteByPlanId(String planId);
}
