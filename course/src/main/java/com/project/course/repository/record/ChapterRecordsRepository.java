package com.project.course.repository.record;

import com.project.course.domain.record.ChapterRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 11:04
 * @version: 1.0
 * @description:
 */
public interface ChapterRecordsRepository extends JpaRepository<ChapterRecords, String> {

    @Transactional(readOnly = true)
    Optional<ChapterRecords> findByIsValidatedEqualsAndStudentIdAndCourseIdAndChapterId(String isValidated, String studentId, String courseId, String chapterId);

    /**
     * 查询有效课程观看学习记录分页信息
     */
    @Transactional(readOnly = true)
    Page<ChapterRecords> findByIsValidatedEqualsAndStudentIdAndCourseIdOrderByUpdateTimeDesc(String isValidated, String studentId, String courseId, Pageable pageable);

    @Transactional(readOnly = true)
    List<ChapterRecords> findAllByIsValidatedEqualsAndCourseIdAndStudentId(String isValidated, String courseId, String studentId);
}
