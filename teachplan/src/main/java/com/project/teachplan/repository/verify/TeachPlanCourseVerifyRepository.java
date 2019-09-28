package com.project.teachplan.repository.verify;

import com.project.teachplan.domain.verify.TeachPlanCourseVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 10:16
 * @version: 1.0
 * @description:
 */
public interface TeachPlanCourseVerifyRepository extends JpaRepository<TeachPlanCourseVerify, String> {

    @Transactional(readOnly = true)
    List<TeachPlanCourseVerify> findAllByVerifyStatusEqualsAndPlanId(String isValidated, String planId);

    @Modifying(clearAutomatically = true)
    int deleteAllByPlanId(String planId);
}
