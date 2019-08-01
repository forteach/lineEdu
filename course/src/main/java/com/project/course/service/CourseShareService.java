package com.project.course.service;


import com.project.course.domain.Course;
import com.project.course.domain.CourseShare;
import com.project.course.domain.CourseShareUsers;
import com.project.course.web.vo.RTeacher;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-5 15:34
 * @Version: 1.0
 * @Description:
 */
public interface CourseShareService {
    /**
     * 根据课程分享编号获得被分享用户列表
     *
     * @param shareId
     * @return
     */
    public List<CourseShareUsers> findByShareIdUsers(String shareId);

    public String save(Course course, List<RTeacher> teacherList);

    public String update(String lessonPreType, String shareId, Course course, List<RTeacher> teacherList);

    /**
     * 根据课程编号查询所有领域信息
     *
     * @param courseId
     * @return
     */
    public CourseShare findByCourseIdAll(String courseId);
}
