package com.project.course.repository.verify;

import com.project.course.domain.CourseChapter;
import com.project.course.domain.verify.CourseChapterVerify;
import com.project.course.repository.dto.ICourseChapterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 09:30
 * @version: 1.0
 * @description:
 */
public interface CourseChapterVerifyRepository extends JpaRepository<CourseChapterVerify, String> {

    /**
     * 根据查询科目编号查询父章节编号是空的章节并按照正序排列
     * 查询条件 1、有效 2 父章节编号是空 3 按照层级正序排列
     *
     * @param courseId 　科目ID
     * @return 章节目录基本信息
     */
    @Transactional(readOnly = true)
    @Query("select chapterId as chapterId, chapterName as chapterName, chapterParentId as chapterParentId, " +
            " publish as publish, sort as sort, chapterLevel as chapterLevel, randomQuestionsNumber as randomQuestionsNumber, " +
            " videoTime as videoTime " +
            " from CourseChapter where  courseId = ?1 order by chapterLevel asc, sort asc")
    List<ICourseChapterDto> findByCourseId(String courseId);

    /**
     * 根据章节信息查询对应小节信息
     *
     * @param isValidated
     * @param chapterParentId
     * @return 所属的章节信息按照从顺序排列
     */
    @Transactional(readOnly = true)
    @Query(" select chapterId as chapterId, chapterName as chapterName, chapterParentId as chapterParentId," +
            " publish as publish, sort as sort, chapterLevel as chapterLevel, randomQuestionsNumber as randomQuestionsNumber," +
            " videoTime as videoTime " +
            " from CourseChapter" +
            " where isValidated = :isValidated and chapterParentId = :chapterParentId order by sort asc")
    List<ICourseChapterDto> findByChapterParentId(@Param("isValidated") String isValidated, @Param("chapterParentId") String chapterParentId);

    /**
     * 根据章节ID和是否有效查询章节目录信息
     *
     * @param isValidated 　是否有效 0 有效 1无效
     * @param courseId    　科目ID
     * @return　目录章节基本信息
     */
    @Transactional(readOnly = true)
    @Query("select chapterId as chapterId, chapterName as chapterName, chapterParentId as chapterParentId, " +
            " publish as publish, sort as sort, chapterLevel as chapterLevel, randomQuestionsNumber as randomQuestionsNumber, " +
            " videoTime as videoTime " +
            " from CourseChapter where isValidated = :isValidated and  courseId = :courseId  ORDER BY  sort asc")
    List<ICourseChapterDto> findCourseId(@Param("isValidated") String isValidated, @Param("courseId") String courseId);

    /**
     * 查询有效的章节科目信息行数
     *
     * @param isValidated
     * @param courseId
     * @param chapterParentId
     * @return
     */
    @Transactional(readOnly = true)
    int countByIsValidatedEqualsAndCourseIdAndChapterParentId(String isValidated, String courseId, String chapterParentId);

    @Transactional(readOnly = true)
    List<CourseChapter> findByCourseIdAndAndChapterParentId(String courseId, String chapterParentId);

    /**
     * 根据id批量删除
     */
    @Modifying
    @Transactional
    @Query(value = "delete from CourseChapter c where c.chapterId in (:ids) ")
    int deleteBathIds(@Param("ids") Set<String> ids);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = " update CourseChapter c set c.isValidated = :isValidated where c.chapterId in (:ids) ")
    int updateIsValidatedIds(@Param("isValidated") String isValidated, @Param("ids") Set<String> ids);
}
