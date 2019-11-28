package com.project.teachplan.repository.verify;

import com.project.teachplan.domain.TeachPlanCourse;
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

    @Modifying(clearAutomatically = true)
    void deleteAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<TeachPlanCourseVerify> findAllByPlanId(String planId);

    @Transactional(readOnly = true)
    List<TeachPlanCourseVerify> findAllByCourseIdAndTeacherId(String courseId, String teacherId);

    @Transactional(readOnly = true)
    List<TeachPlanCourseVerify> findAllByTeacherId(String teacherId);

    @Modifying
    void deleteAllByCourseIdAndTeacherId(String courseId, String teacherId);
}