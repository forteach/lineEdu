package com.project.course.repository;

import com.project.course.domain.CourseStudy;
import com.project.course.repository.dto.ICourseStudyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-27 15:27
 * @version: 1.0
 * @description: 课程学习数据
 */
public interface CourseStudyRepository extends JpaRepository<CourseStudy, String>, JpaSpecificationExecutor<CourseStudy> {

    @Modifying
    void deleteAllByStudentId(String studentId);
    /**
     * 查询学生学习课程的状态信息
     *
     * @param studentId
     * @return
     */
    @Query(value = "select " +
            " c.course_id as courseId, " +
            " c.course_name as courseName, " +
            " c.alias as alias, " +
            " cc.chapter_id as chapterId, " +
            " cc.chapter_name as chapterName, " +
            " cs.study_status as studyStatus, " +
            " cs.semester_grade as semesterGrade," +
            " cs.exam_grade as examGrade, " +
            " cs.exam_results as examResults, " +
            " cs.make_up_examination as makeUpExamination " +
            " from course_study as cs " +
            " left join course as c on c.course_id = cs.course_id " +
            " left join course_chapter cc on cc.chapter_id = cs.chapter_id " +
            " where cs.is_validated = '0' and cs.student_id = ?1 " +
            " and cs.study_status = ?2 OR ?2 IS NULL OR ?2 ='' " +
            " order by cs.c_time desc ", nativeQuery = true)
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    List<ICourseStudyDto> findByIsValidatedEqualsAndStudentId(String studentId, Integer studyStatus);

    @Transactional(readOnly = true)
    Optional<CourseStudy> findAllByCourseIdAndStudentId(String courseId, String studentId);

    @Transactional(readOnly = true)
    List<CourseStudy> findAllByIsValidatedEqualsAndCreateTimeAfter(String isValidated, String createTime);

    @Query(value = "select " +
            " c.course_id as courseId, " +
            " c.course_name as courseName, " +
            " c.alias as alias, " +
            " cs.study_status as studyStatus, " +
            " cs.semester_grade as semesterGrade," +
            " cs.exam_grade as examGrade, " +
            " cs.exam_results as examResults, " +
            " cs.make_up_examination as makeUpExamination, " +
            " s.student_id as studentId, " +
            " s.student_name as studentName, " +
            " cs.on_line_time as onLineTime, " +
            " cs.on_line_time_sum as onLineTimeSum, " +
            " cs.answer_sum as answerSum, " +
            " cs.correct_sum as correctSum " +
            " from course_study as cs " +
            " left join student_on_line as s on s.student_id = cs.student_id " +
            " left join course as c on c.course_id = cs.course_id " +
            " where cs.is_validated = ?1 and cs.course_id = ?2 order by cs.c_time desc ",
            countQuery = "select count (*) from course_study as cs " +
                    " left join student_on_line as s on s.student_id = cs.student_id " +
                    " left join course as c on c.course_id = cs.course_id " +
                    " where cs.is_validated = ?1 and cs.course_id = ?2 order by cs.c_time desc ",
            nativeQuery = true)
    @Transactional(readOnly = true)
    Page<ICourseStudyDto> findAllByIsValidatedEqualsAndCourseIdOrderByCreateTimeDesc(String isValidated, String courseId, Pageable pageable);

    @Query(value = "select " +
            " c.course_id as courseId, " +
            " c.course_name as courseName, " +
            " c.alias as alias, " +
            " cs.study_status as studyStatus, " +
            " cs.semester_grade as semesterGrade," +
            " cs.exam_grade as examGrade, " +
            " cs.exam_results as examResults, " +
            " cs.make_up_examination as makeUpExamination, " +
            " s.student_id as studentId, " +
            " s.student_name as studentName, " +
            " cs.on_line_time as onLineTime, " +
            " cs.on_line_time_sum as onLineTimeSum, " +
            " cs.answer_sum as answerSum, " +
            " cs.correct_sum as correctSum " +
            " from course_study as cs " +
            " left join student_on_line as s on s.student_id = cs.student_id " +
            " left join course as c on c.course_id = cs.course_id " +
            " where cs.is_validated = ?1 and cs.course_id = ?2 and cs.student_id = ?3 order by cs.c_time desc ",
            countQuery = "select count (*) from course_study as cs " +
                    " left join student_on_line as s on s.student_id = cs.student_id " +
                    " left join course as c on c.course_id = cs.course_id " +
                    " where cs.is_validated = ?1 and cs.course_id = ?2 " +
                    " and cs.student_id = ?3 order by cs.c_time desc ",
            nativeQuery = true)
    @Transactional(readOnly = true)
    Page<ICourseStudyDto> findAllByIsValidatedEqualsAndCourseIdAndStudentIdOrderByCreateTimeDesc(String isValidated, String courseId, String studentId, Pageable pageable);

//    @Query(value = "select " +
//            " cs.course_Id as courseId, " +
//            " cs.on_line_time as onLineTime, " +
//            " cs.on_line_time_sum as onLineTimeSum, " +
//            " cs.answer_sum as answerSum, " +
//            " cs.correct_sum as correctSum, " +
//            " vo.video_percentage as videoPercentage, " +
//            " vo.jobs_percentage as jobsPercentage " +
//            " from (select distinct course_id, jobs_percentage, video_percentage from lineEdu.course " +
//            " where course_number = ?2 and course.c_user = ?3) as vo " +
//            " left join lineEdu.course_study as cs on vo.course_id = cs.course_id where cs.is_validated = '0' " +
//            " and cs.student_id = ?1", nativeQuery = true)
//    @Transactional(readOnly = true)
//    Optional<ICourseStudyDto> findStudyDto(String studentId, String courseNumber, String teacherId);


    @Query(value = "select " +
            " cs.courseId as courseId, " +
            " cs.onLineTime as onLineTime, " +
            " cs.onLineTimeSum as onLineTimeSum, " +
            " cs.answerSum as answerSum, " +
            " cs.correctSum as correctSum, " +
            " c.videoPercentage as videoPercentage, " +
            " c.jobsPercentage as jobsPercentage " +
            " from CourseStudy AS cs left join Course as c on c.courseId = cs.courseId " +
            " where c.isValidated = '0' and cs.isValidated = '0' and cs.studentId = ?1 and cs.courseId =?2 ")
    @Transactional(readOnly = true)
    Optional<ICourseStudyDto> findStudyDto(String studentId, String courseId);
}