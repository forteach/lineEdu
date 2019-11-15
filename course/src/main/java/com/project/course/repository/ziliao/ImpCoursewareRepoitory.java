package com.project.course.repository.ziliao;

import com.project.course.domain.ziliao.ImportantCourseware;
import com.project.course.repository.dto.IVideoTimeSumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ImpCoursewareRepoitory extends JpaRepository<ImportantCourseware, String> {

    int deleteAllByChapterId(String chapterId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "delete from ImportantCourseware where chapterId in (:ids) ")
    int deleteAllByBathIds(@Param("ids") Set<String> ids);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findByIsValidatedEqualsAndChapterId(String isValidated, String chapterId);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findAllByIsValidatedEqualsAndChapterIdAndVerifyStatus(String isValidated, String chapterId, String verifyStatus);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findAllByIsValidatedEqualsAndCourseIdAndDatumTypeAndVerifyStatus(String isValidated, String courseId, String datumType, String verifyStatus);

    @Query(value = "select sum(videoTime) as videoTimeSum from ImportantCourseware " +
            " where isValidated = '0' and verifyStatus = '0' and datumType = '3' and importantType = '2' and courseId = ?1")
    @Transactional(readOnly = true)
    IVideoTimeSumDto findVideoTimeSumByCourseId(String courseId);
}
