package com.project.teachplan.repository.verify;

import com.project.teachplan.domain.verify.TeachPlanVerify;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<TeachPlanVerify> findAllByIsValidatedEqualsAndPlanId(String isValidated, String planId);
}