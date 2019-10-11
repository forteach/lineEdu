package com.project.course.service;


import com.project.course.domain.Course;
import com.project.course.domain.CourseImages;
import com.project.course.domain.verify.CourseVerify;
import com.project.course.repository.dto.ICourseDto;
import com.project.course.repository.dto.ICourseListDto;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.web.req.CourseImagesReq;
import com.project.course.web.resp.CourseListResp;
import com.project.course.web.vo.CourseVerifyVo;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 15:55
 * @Version: 1.0
 * @Description:
 */
public interface CourseService {

    public String saveUpdate(CourseVerify course);

//    public String edit(Course course);

    public void deleteIsValidById(String courseId);

    public void deleteById(String courseId);

    public void delete(Course course);

    public Course findByCourseId(String courseId);

    public CourseVerify findCourseVerifyById(String courseId);

    public List<ICourseListDto> findAll(PageRequest page);

    public Map<String, Object> getCourseById(String courseId);

    public List<CourseImages> findImagesByCourseId(String courseId, String verifyStatus);

    public void saveCourseImages(CourseImagesReq courseImagesReq);

    public List<ICourseListDto> findMyCourse(String userId, PageRequest page);

    public CourseVerify getById(String id);

    public List<CourseListResp> myCourseList(String classId);

    public List<ICourseStudyDto> findCourseStudyList(String studentId, Integer studyStatus);

    public int deleteImagesByCourseId(String courseId);

    ICourseDto findByCourseNumberAndTeacherId(String courseNumber, String teacherId);

    void verifyCourse(CourseVerifyVo verifyVo);

//    void verifyCourseImage(CourseVerifyVo verifyVo);
}