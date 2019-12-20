package com.project.course.service;


import com.project.course.domain.Course;
import com.project.course.domain.CourseImages;
import com.project.course.repository.dto.ICourseListDto;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.web.req.CourseImagesReq;
import com.project.course.web.resp.CourseListResp;
import com.project.course.web.vo.CourseTeacherVo;
import com.project.course.web.vo.CourseVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 15:55
 * @Version: 1.0
 * @Description:
 */
public interface CourseService {

    public String saveUpdate(Course course);

    public void updateStatusById(String courseId, String userId);

    public void deleteById(String courseId);

    public void delete(Course course);

    public List<ICourseListDto> findAll(PageRequest page);

    List<ICourseListDto> findAllByCourseType(Integer courseType, PageRequest page);

    public List<CourseImages> findImagesByCourseId(String courseId, String verifyStatus);

    public void saveCourseImages(CourseImagesReq courseImagesReq);

    public List<ICourseListDto> findMyCourse(String userId, PageRequest page);

    List<ICourseListDto> findMyCourse(PageRequest page);

    public Course getById(String id);

    public List<CourseListResp> myCourseList(String classId);

    public List<ICourseStudyDto> findCourseStudyList(String studentId, Integer studyStatus);

    public int deleteImagesByCourseId(String courseId);

    List<CourseVo> findByCourseNumber(List<CourseTeacherVo> courseIds, String courseType, String classId, String studentId, String key);

    List<CourseVo> findCourseVoByClassId(String classId, String key);

//    List<Course> findAllCourseVoByCreateUser(String createUser);

    void updateCourseTime(String courseId, Integer videoTimeNum);

    void taskCourseStudy();

    void taskCourseQuestions();

    List<Course> findAll();

    Page<ICourseStudyDto> findCourseStudyPageAll(String courseId, String studentId, PageRequest pageRequest);

//    void updatePublish(String courseId, String userId);
}