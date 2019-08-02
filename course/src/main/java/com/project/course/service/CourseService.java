package com.project.course.service;


import com.project.course.domain.Course;
import com.project.course.domain.CourseEntity;
import com.project.course.domain.CourseImages;
import com.project.course.repository.dto.ICourseListDto;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.web.req.CourseImagesReq;
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

//    public String edit(Course course);

    public void deleteIsValidById(String courseId);

    public void deleteById(String courseId);

    public void delete(Course course);

    public Course findByCourseId(String courseId);

    public List<ICourseListDto> findAll(PageRequest page);

//    public Map<String, Object> getCourseById(String courseId);

    public List<CourseImages> findImagesByCourseId(String courseId);

    public void saveCourseImages(CourseImagesReq courseImagesReq);

    public List<ICourseListDto> findMyCourse(String userId, PageRequest page);

    public Course getById(String id);

//    public List<CourseListResp> myCourseList(String classId);

    public List<CourseEntity> findCourseList();

    public List<ICourseStudyDto> findCourseStudyList(String studentId, Integer studyStatus);

    public int deleteImagesByCourseId(String courseId);
}