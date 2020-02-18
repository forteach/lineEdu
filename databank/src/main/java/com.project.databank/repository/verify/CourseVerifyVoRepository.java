package com.project.databank.repository.verify;

import com.project.databank.domain.verify.CourseVerifyVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:35
 * @version: 1.0
 * @description:
 */
public interface CourseVerifyVoRepository extends JpaRepository<CourseVerifyVo, String> {
    @Modifying(clearAutomatically = true)
    void deleteAllByCourseIdAndChapterIdAndVerifyStatusAndCourseType(String courseId, String chapterId, String verifyStatus, String courseType);

    @Modifying(flushAutomatically = true)
    void deleteAllByCourseIdAndChapterId(String courseId, String chapterId);

    @Transactional(readOnly = true)
    Page<CourseVerifyVo> findAllByIsValidatedEqualsAndVerifyStatusOrderByCreateTimeDesc(String isValidated, String verifyStatus, Pageable pageable);

    @Transactional(readOnly = true)
    Page<CourseVerifyVo> findAllByIsValidatedEqualsAndVerifyStatusAndCourseIdOrderByCreateTimeDesc(String isValidated, String verifyStatus, String courseId, Pageable pageable);

    @Modifying(flushAutomatically = true)
    int deleteByFileId(String fileId);

//    @Query(value = "select distinct courseName from CourseVerifyVo where isValidated = '0' and verifyStatus = '1'")
//    List<String> findDistinctAllByIsValidatedEqualsAndVerifyStatus();

    @Transactional(readOnly = true)
    boolean existsAllByIsValidatedEqualsAndCourseIdAndVerifyStatus(String isValidated, String courseId, String verifyStatus);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteAllByCourseId(String courseId);

    @Modifying(flushAutomatically = true)
    void deleteAllByCourseIdAndChapterIdIn(String courseId, Set<String> chapterIds);
}