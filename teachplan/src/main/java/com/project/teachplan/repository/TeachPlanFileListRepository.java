package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlanFileList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 11:48
 * @version: 1.0
 * @description:
 */
public interface TeachPlanFileListRepository extends JpaRepository<TeachPlanFileList, String> {
    @Transactional(readOnly = true)
    Page<TeachPlanFileList> findAllByIsValidatedEqualsAndPlanIdOrderByCreateDate(String isValidated, String planId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<TeachPlanFileList> findAllByIsValidatedEqualsAndPlanIdAndCreateDateOrderByCreateDate(String isValidated, String planId, String createDate, Pageable pageable);

    @Modifying(clearAutomatically = true)
    int deleteAllByPlanIdAndClassIdAndCourseIdAndCreateDate(String planId, String classId, String courseId, String createDate);

    Optional<TeachPlanFileList> findByPlanIdAndClassIdAndCourseIdAndCreateDate(String planId, String classId, String courseId, String createDate);

    List<TeachPlanFileList> findAllByPlanId(String planId);
}
