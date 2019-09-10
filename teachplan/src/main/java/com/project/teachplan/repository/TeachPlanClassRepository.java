package com.project.teachplan.repository;

import com.project.teachplan.domain.online.TeachPlanClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeachPlanClassRepository extends JpaRepository<TeachPlanClass, String> {
    @Modifying(flushAutomatically = true)
    int deleteAllByPlanId(String planId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TeachPlanClass set isValidated = ?1 where planId = ?2")
    int updateIsValidatedByPlanId(String isValidated, String planId);

    @Transactional(readOnly = true)
    List<TeachPlanClass> findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(String isValidated, String planId);
}
