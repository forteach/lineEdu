package com.project.course.repository.ziliao;

import com.project.course.domain.ziliao.ImportantCourseware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImpCoursewareRepoitory extends JpaRepository<ImportantCourseware, String> {

    /**
     * 重要课件资料（教案、课件）图集类型除外,按章节、文件类型、课件类型查询
     * <p>
     * //     * @param chapterId
     * //     * @param datumType
     * //     * @param importantType 1、教案 2、课件
     * //     * @param isValidated
     *
     * @return
     */
//    @Transactional(readOnly = true)
//    public List<ImportantCourseware> findByChapterIdAndDatumTypeAndImportantTypeAndIsValidated(String chapterId, String datumType, String importantType, String isValidated);
    @Modifying(clearAutomatically = true)
    public int deleteAllByChapterId(String chapterId);

    @Transactional(readOnly = true)
    public List<ImportantCourseware> findByIsValidatedEqualsAndChapterId(String isValidated, String chapterId);
}
