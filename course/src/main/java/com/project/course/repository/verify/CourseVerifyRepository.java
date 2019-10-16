//package com.project.course.repository.verify;
//
//import com.project.course.domain.Course;
//import com.project.course.domain.verify.CourseVerify;
//import com.project.course.repository.dto.ICourseChapterListDto;
//import com.project.course.repository.dto.ICourseDto;
//import com.project.course.repository.dto.ICourseListDto;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-9-29 09:22
// * @version: 1.0
// * @description:
// */
//public interface CourseVerifyRepository extends JpaRepository<CourseVerify, String> {
//    Course findByCourseId(String courseId);
//
//    /**
//     * 分页查询所有有效课程
//     *
//     * @param isValidated
//     * @param pageable
//     * @return
//     */
//    @Transactional(readOnly = true)
//    Page<ICourseListDto> findByIsValidated(String isValidated, Pageable pageable);
//
//    /**
//     * 分页查询我的课程科目
//     *
//     * @param isValidated
//     * @param cUser
//     * @param pageable
//     * @return
//     */
//    @Transactional(readOnly = true)
//    Page<ICourseListDto> findByCreateUserAndIsValidatedOrderByCreateTimeDesc(String cUser, String isValidated, Pageable pageable);
//
//    /**
//     * 分页查询课程信息根据课程id查询课程列表
//     *
//     * @param classId
//     * @return
//     */
//    @Query(value = " select " +
//            "  c.courseId       as courseId, " +
//            "  c.courseName     as courseName, " +
//            "  c.alias          as alias, " +
//            "  c.topPicSrc     as topPicSrc, " +
//            "  c.courseDescribe as courseDescribe, " +
//            "  t.teacherId      as teacherId, " +
//            "  t.teacherName    as teacherName " +
//            " from CourseVerify as c " +
//            " left join TeacherClassCourse as tcc on c.courseNumber = tcc.courseId " +
//            " left join Teacher as t on t.teacherId= tcc.teacherId " +
//            " where c.isValidated = '0' " +
//            " and t.isValidated = '0' " +
//            " and tcc.isValidated = '0'" +
//            " and c.createUser = tcc.teacherId " +
//            " and tcc.classId = ?1 " +
//            " order by c.createTime ")
//    @Transactional(readOnly = true, rollbackFor = Exception.class)
//    List<ICourseChapterListDto> findByIsValidatedEqualsAndCourseIdInOrderByCreateTime(String classId);
//
//    @Transactional(readOnly = true)
//    List<Course> findAllByIsValidatedEqualsAndCourseNumberAndCreateUserOrderByCreateTimeDesc(String isValidated, String courseNumber, String cUser);
//
//
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
//            " t.teacherName as createUserName " +
//            " from CourseVerify as c " +
//            " left join Teacher as t on t.teacherId = c.createUser " +
//            " where c.isValidated = '0' " +
//            " and c.courseNumber = ?1 " +
//            " and c.createUser = ?2 order by c.createTime desc")
//    @Transactional(readOnly = true)
//    List<ICourseDto> findAllByCourseNumberAndCreateUserOrderByCreateTimeDescDto(String courseNumber, String cUser);
//}