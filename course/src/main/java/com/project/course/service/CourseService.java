package com.project.course.service;


import com.project.course.domain.Course;
import com.project.course.domain.CourseImages;
import com.project.course.repository.dto.ICourseListDto;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.web.req.CourseImagesReq;
import com.project.course.web.resp.CourseListResp;
import com.project.course.web.vo.CourseAllStudyVo;
import com.project.course.web.vo.CourseTeacherVo;
import com.project.course.web.vo.CourseVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 15:55
 * @Version: 1.0
 * @Description:
 */
public interface CourseService {

    String saveUpdate(Course course);

    void updateStatusById(String courseId, String userId);

    void deleteById(String courseId);

    void delete(Course course);

    List<ICourseListDto> findAll(PageRequest page);

    List<ICourseListDto> findAllByCourseType(Integer courseType, PageRequest page);

    List<CourseImages> findImagesByCourseId(String courseId, String verifyStatus);

    void saveCourseImages(CourseImagesReq courseImagesReq);

    List<ICourseListDto> findMyCourse(PageRequest page);

    Course getById(String id);

    List<CourseListResp> myCourseList(String classId);

    List<ICourseStudyDto> findCourseStudyList(String studentId, Integer studyStatus);

    int deleteImagesByCourseId(String courseId);

    List<CourseVo> findByCourseNumber(List<CourseTeacherVo> courseIds, String classId, String studentId, String key);

    List<CourseVo> findCourseVoByClassId(String classId, String key);

    void updateCourseTime(String courseId, Integer videoTimeNum);

    void taskUpdateVideoTimeSum();

    void taskCourseStudy();

    void taskCourseQuestions();

    List<Course> findAll();

    List<Course> findAllByCourseId(List<String> courseIds);

    List<Course> findCourseListByKey(String key);

    void setCourseListRedis(List<Course> list, String key);

    Page<ICourseStudyDto> findCourseStudyPageAll(String courseId, String studentId, PageRequest pageRequest);

    Page<ICourseStudyDto> findAllByStudentId(String studentId, Pageable pageable);

    List<Course> findAllByCourseNumberIds(List<String> courseNumberIds);

    CourseAllStudyVo findCourseAllStudyByStudentId(String studentId, List<String> courseIds, List<String> offlineIds);
}