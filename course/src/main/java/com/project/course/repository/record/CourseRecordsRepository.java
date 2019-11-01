package com.project.course.repository.record;

import com.project.course.domain.Course;
import com.project.course.domain.record.CourseRecords;
import com.project.course.repository.dto.ChapterRecordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 11:05
 * @version: 1.0
 * @description:
 */
public interface CourseRecordsRepository extends JpaRepository<CourseRecords, String> {

    @Transactional(readOnly = true)
    List<CourseRecords> findAllByIsValidatedEqualsAndCreateTimeAfter(String isValidated, String createTime);

    @Transactional(readOnly = true)
    Optional<CourseRecords> findByIsValidatedEqualsAndStudentIdAndCourseId(String isValidated, String studentId, String courseId);

    /** 查询有效的分页学生学习记录信息*/
    @Transactional(readOnly = true)
    Page<CourseRecords> findByIsValidatedEqualsAndStudentIdOrderByUpdateTimeDesc(String isValidated, String studentId, Pageable pageable);

    @Query(value = "select " +
            " chapterId as chapterId, chapterName as chapterName from CourseChapter where chapterId = (select distinct chapterId from CourseRecords " +
            " where isValidated = '0' and studentId = ?1 and courseId = ?2) ")
    @Transactional(readOnly = true)
    ChapterRecordDto findDtoByStudentIdAndCourseId(String studentId, String courseId);

    @Transactional(readOnly = true)
    Page<CourseRecords> findAllByIsValidatedEqualsAndCenterAreaIdAndCreateTimeAfterOrderByUpdateTimeDesc(String isValidated, String centerAreaId, String createTime, Pageable pageable);

    @Transactional(rollbackFor = Exception.class)
    Page<CourseRecords> findAllByIsValidatedEqualsAndCreateTimeAfterOrderByUpdateTimeDesc(String isValidated, String createTime, Pageable pageable);

    @Transactional(readOnly = true)
    Page<CourseRecords> findAllByIsValidatedEqualsAndCourseIdAndCreateTimeAfterOrderByUpdateTimeDesc(String isValidated, String courseId, String createTime, Pageable pageable);

    @Transactional(readOnly = true)
    Page<CourseRecords> findAllByIsValidatedEqualsAndCenterAreaIdAndCourseIdAndCreateTimeAfterOrderByUpdateTimeDesc(String isValidated, String centerAreaId, String courseId, String createTime, Pageable pageable);
}