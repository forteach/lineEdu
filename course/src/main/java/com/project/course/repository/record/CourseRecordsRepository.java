package com.project.course.repository.record;

import com.project.course.domain.record.CourseRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
    Optional<CourseRecords> findByIsValidatedEqualsAndStudentIdAndCourseId(String isValidated, String studentId, String courseId);

    /** 查询有效的分页学生学习记录信息*/
    @Transactional(readOnly = true)
    Page<CourseRecords> findByIsValidatedEqualsAndStudentIdOrderByUpdateTimeDesc(String isValidated, String studentId, Pageable pageable);
}
