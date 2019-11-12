package com.project.course.repository.ziliao;

import com.project.course.domain.ziliao.ImportantCourseware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImpCoursewareRepoitory extends JpaRepository<ImportantCourseware, String> {

    int deleteAllByChapterId(String chapterId);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findByIsValidatedEqualsAndChapterId(String isValidated, String chapterId);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findAllByIsValidatedEqualsAndChapterIdAndVerifyStatus(String isValidated, String chapterId, String verifyStatus);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findAllByIsValidatedEqualsAndCourseIdAndDatumTypeAndVerifyStatus(String isValidated, String courseId, String datumType, String verifyStatus);
}
