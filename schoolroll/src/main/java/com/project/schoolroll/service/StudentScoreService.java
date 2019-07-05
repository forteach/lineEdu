package com.project.schoolroll.service;

import com.project.schoolroll.domain.StudentScore;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:35
 * @version: 1.0
 * @description:
 */
public interface StudentScoreService {
    public StudentScore findByStudentIdAndCourseId(String studentId, String courseId);
    public List<StudentScore> findByStudentId(String studentId);
    public void deleteStudentScoreById(String scoreId);
}
