package com.project.teachplan.repository.verify;

import com.project.teachplan.domain.verify.PlanFileVerity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 18:13
 * @version: 1.0
 * @description:
 */
public interface PlanFileVerityRepository extends JpaRepository<PlanFileVerity, String> {

    @Transactional(readOnly = true)
    List<PlanFileVerity> findAllByIsValidatedEqualsAndPlanId(String isValidated, String planId);
}
