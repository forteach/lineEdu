package com.project.course.repository.ziliao;

import com.project.course.domain.ziliao.ImportantCourseware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImpCoursewareRepoitory extends JpaRepository<ImportantCourseware, String> {

    public int deleteAllByChapterId(String chapterId);

    @Transactional(readOnly = true)
    public List<ImportantCourseware> findByIsValidatedEqualsAndChapterId(String isValidated, String chapterId);

    @Transactional(readOnly = true)
    List<ImportantCourseware> findAllByIsValidatedEqualsAndChapterIdAndVerifyStatus(String isValidated, String chapterId, String verifyStatus);
}
