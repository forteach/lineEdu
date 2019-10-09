package com.project.teachplan.repository.verify;

import com.project.teachplan.domain.TeachPlanClass;
import com.project.teachplan.domain.verify.TeachPlanClassVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 11:20
 * @version: 1.0
 * @description:
 */
public interface TeachPlanClassVerifyRepository extends JpaRepository<TeachPlanClassVerify, String> {

    @Transactional(readOnly = true)
    List<TeachPlanClassVerify> findAllByVerifyStatusAndPlanId(String verifyStatus, String planId);

    @Modifying(flushAutomatically = true)
    int deleteAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<TeachPlanClassVerify> findAllByPlanId(String planId);
}
