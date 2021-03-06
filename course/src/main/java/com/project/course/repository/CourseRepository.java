package com.project.course.repository;

import com.project.course.domain.Course;
import com.project.course.repository.dto.ICourseChapterListDto;
import com.project.course.repository.dto.ICourseDto;
import com.project.course.repository.dto.ICourseListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-15 16:29
 * @Version: 1.0
 * @Description:　科目
 */
public interface CourseRepository extends JpaRepository<Course, String> {

//    public Course findByCourseId(String courseId);

//    /**
//     * 分页查询所有有效课程
//     *
//     * @param isValidated
//     * @param pageable
//     * @return
//     */
//    @Transactional(readOnly = true)
//    Page<ICourseListDto> findByIsValidated(String isValidated, Pageable pageable);

    @Transactional(readOnly = true)
    Page<ICourseListDto> findAllByIsValidatedNotNull(Pageable pageable);

    @Transactional(readOnly = true)
    Page<ICourseListDto> findAllByCourseType(Integer courseType, Pageable pageable);

    boolean existsAllByCourseName(String courseName);

//    /**
//     * 分页查询我的课程科目
//     *
//     * @param cUser
//     * @param pageable
//     * @return
//     */
//    @Transactional(readOnly = true)
//    Page<ICourseListDto> findByCreateUserOrderByCreateTimeDesc(String cUser, Pageable pageable);

    @Transactional(readOnly = true)
    Page<ICourseListDto> findByOrderByCreateTimeDesc(Pageable pageable);

    /**
     * 分页查询课程信息根据课程id查询课程列表
     *
     * @param classId
     * @return
     */
    @Query(value = " select " +
            "  c.courseId       as courseId, " +
            "  c.courseName     as courseName, " +
            "  c.alias          as alias, " +
            "  c.topPicSrc      as topPicSrc, " +
            "  c.courseDescribe as courseDescribe, " +
            "  c.courseType     as courseType, " +
            "  t.teacherId      as teacherId, " +
            "  t.teacherName    as teacherName " +
            " from Course as c " +
            " left join TeacherClassCourse as tcc on c.courseNumber = tcc.courseId " +
            " left join Teacher as t on t.teacherId= tcc.teacherId " +
            " where c.isValidated = '0' " +
            " and t.isValidated = '0' " +
            " and tcc.isValidated = '0'" +
            " and c.createUser = tcc.teacherId " +
            " and tcc.classId = ?1 " +
            " order by c.createTime ")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    List<ICourseChapterListDto> findByIsValidatedEqualsAndCourseIdInOrderByCreateTime(String classId);

//    @Query(value = "select " +
//            " c.courseId as courseId, " +
//            " c.courseName as courseName, " +
//            " c.courseNumber as courseNumber, " +
//            " c.alias as alias, " +
//            " c.topPicSrc as topPicSrc, " +
//            " c.courseDescribe as courseDescribe, " +
//            " c.learningTime as learningTime, " +
//            " c.videoPercentage as videoPercentage, " +
//            " c.jobsPercentage as jobsPercentage, " +
//            " c.createUser as createUser, " +
//            " c.courseType as courseType, " +
//            " t.teacherName as createUserName " +
//            " from Course as c " +
//            " left join Teacher as t on t.teacherId = c.createUser " +
//            " where c.isValidated = '0' " +
//            " and c.courseNumber = ?1 " +
//            " and c.createUser = ?2 order by c.createTime desc")
//    @Transactional(readOnly = true)
//    List<ICourseDto> findAllByCourseNumberAndCreateUserOrderByCreateTimeDescDto(String courseNumber, String cUser);

//    @Transactional(readOnly = true)
//    List<ICourseDto> findAllByIsValidatedEqualsAndCourseNumberOrderByCreateTimeDesc(String isValidated, String courseNumber);

    @Transactional(readOnly = true)
    List<ICourseDto> findAllByIsValidatedEqualsAndCourseNumberAndCourseTypeInOrderByCreateTimeDesc(String isValidated, String courseNumber, List<Integer> courseTypes);

//    List<Course> findAllByIsValidatedEqualsAndCourseNumberInAndCourseTypeIn(String isValidated, List<String> courseNumbers, List<Integer> courseTypes);

//    @Transactional(readOnly = true)
//    List<Course> findAllByCreateUserOrderByCreateTimeDesc(String cUser);

    @Transactional(readOnly = true)
    List<Course> findAllByOrderByCreateTimeDesc();

    @Transactional(readOnly = true)
    List<Course> findAllByCourseIdInOrderByCreateTimeDesc(List<String> list);

//    @Query(value = "select " +
//            " c.courseId as courseId,c.courseName as courseName,c.courseNumber as courseNumber, " +
//            " c.courseDescribe as courseDescribe,c.alias as alias,c.topPicSrc as topPicSrc, " +
//            " c.learningTime as learningTime, c.videoTimeNum as videoTimeNum, " +
//            " c.videoPercentage as videoPercentage, c.jobsPercentage as jobsPercentage, " +
//            " c.courseType as courseType, c.createUser as createUser, c.updateUser as updateUser, " +
//            " c.createTime as createTime, c.updateTime as updateTime, c.isValidated as isValidated, c.centerAreaId as centerAreaId " +
//            " from Course as c where c.isValidated = '0' and c.courseNumber in " +
//            " (select courseId where TeachPlanCourse where isValidated = '0' and planId in " +
//            " (select planId where isValidated = '0' and classId = ?1)) ")
//    Page<Course> findAllByClassId(String classId, Pageable pageable);

    @Transactional(readOnly = true)
    List<Course> findAllByIsValidatedEqualsAndCourseNumberIn(String isValidated, List<String> courseNumberIds);

    @Transactional(readOnly = true)
    List<Course> findAllByIsValidatedEqualsAndCourseTypeIn(String isValidated, List<Integer> courseTypeIds);
}