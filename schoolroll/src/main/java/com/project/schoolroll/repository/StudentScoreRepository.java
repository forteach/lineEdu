package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.repository.dto.StudentScoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:29
 * @version: 1.0
 * @description:
 */
public interface StudentScoreRepository extends JpaRepository<StudentScore, String>, JpaSpecificationExecutor<StudentScore> {

    @Transactional(readOnly = true)
    Optional<StudentScore> findAllByIsValidatedEqualsAndStudentIdAndCourseId(String isValidated, String studentId, String courseId);

    @Transactional(readOnly = true)
    Optional<StudentScore> findAllByStudentIdAndCourseId(String studentId, String courseId);

    @Query(value = " select " +
            " ss.studentId as studentId, " +
            " c.courseId as courseId, " +
            " c.courseName as courseName, " +
            " ss.courseType as courseType, " +
            " ss.schoolYear as schoolYear," +
            " ss.term as term, " +
            " ss.courseScore as courseScore, " +
            " ss.onLineScore as onLineScore, " +
            " ss.offLineScore as offLineScore, " +
            " ss.type as type " +
            " from StudentScore as ss " +
            " left join Course as c on c.courseId = ss.courseId " +
            " where ss.isValidated = ?1 and c.isValidated = ?1 and ss.studentId in ?2 ")
    List<StudentScoreDto> findAllByIsValidatedEqualsAndStudentIdIn(String isValidated, List<String> studentIds);

    @Query(value = " select " +
            " ss.studentId as studentId, " +
            " c.courseId as courseId, " +
            " c.courseName as courseName, " +
            " ss.courseType as courseType, " +
            " ss.schoolYear as schoolYear," +
            " ss.term as term, " +
            " ss.courseScore as courseScore, " +
            " ss.onLineScore as onLineScore, " +
            " ss.offLineScore as offLineScore, " +
            " ss.type as type " +
            " from StudentScore as ss " +
            " left join Course as c on c.courseId = ss.courseId " +
            " where ss.isValidated = ?1 and c.isValidated = ?1 and ss.studentId = ?2 ")
    List<StudentScoreDto> findAllByIsValidatedEqualsAndStudentId(String isValidated, String studentId);

    @Transactional(readOnly = true)
    List<StudentScore> findAllByIsValidatedEqualsAndStudentIdOrderByUpdateTime(String isValidated, String studentId);

    @Query(value = " select " +
            " ss.studentId as studentId, " +
            " s.stuId as stuId, " +
            " s.studentName as studentName," +
            " s.gender as gender, " +
            " ss.schoolYear as schoolYear," +
            " ss.term as term, " +
            " ss.courseScore as courseScore, " +
            " ss.onLineScore as onLineScore, " +
            " ss.offLineScore as offLineScore, " +
            " ss.courseType as courseType," +
            " ss.type as type " +
            " from StudentScore as ss " +
            " left join StudentOnLine as s on s.studentId = ss.studentId" +
            " where ss.isValidated = '0' " +
            " and ss.centerAreaId = ?1 order by ss.createTime desc ")
    @Transactional(readOnly = true)
    List<StudentScoreDto> findAllByIsValidatedEqualsAndCenterAreaId(String centerAreaId);



    @Query(value = "SELECT * FROM student_score " +
            " where is_validated = '0' " +
            " and (student_id = ?1 OR ?1 IS NULL )" +
            " AND (course_id = ?2 OR ?2 IS NULL )" +
            " AND (term = ?3 OR ?3 IS NULL )" +
            " AND (course_type = ?4 OR ?4 IS NULL )" +
            " AND (school_year = ?5 OR ?5 IS NULL ) " +
            " AND (u_time >= ?6 OR ?6 IS NULL )" +
            " AND (u_time <= ?7 OR ?7 IS NULL )" +
            " ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT(1) FROM " +
                    "SELECT * FROM student_score " +
                    "where is_validated = '0'" +
                    " AND (student_id = ?1 OR ?1 IS NULL )" +
                    " AND (course_id = ?2 OR ?2 IS NULL )" +
                    " AND (term = ?3 OR ?3 IS NULL )" +
                    " AND (course_type = ?4 OR ?4 IS NULL )" +
                    " AND (school_year = ?5 OR ?5 IS NULL )" +
                    " AND (u_time >= ?6 OR ?6 IS NULL )" +
                    " AND (u_time <= ?7 OR ?7 IS NULL )" +
                    " ORDER BY ?#{#pageable}",
            nativeQuery = true)
    @Transactional(readOnly = true)
    Page<StudentScore> findAllPage(String studentId, String courseId, String term, String courseType, String schoolYear,
                                   String startDate, String endDate, Pageable pageable);
}
