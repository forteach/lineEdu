package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlanFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-25 16:38
 * @version: 1.0
 * @description:
 */
public interface TeachPlanFileRepository extends JpaRepository<TeachPlanFile, String> {
    @Transactional(readOnly = true)
    List<TeachPlanFile> findAllByIsValidatedEqualsAndPlanIdOrderByCreateTimeDesc(String isValidated, String planId);
    @Modifying(clearAutomatically = true)
    int deleteAllByPlanId(String planId);
}
