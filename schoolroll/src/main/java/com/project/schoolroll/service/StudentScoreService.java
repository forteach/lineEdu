package com.project.schoolroll.service;

import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.web.vo.OffLineScoreUpdateVo;
import com.project.schoolroll.web.vo.PlanStudentVo;
import com.project.schoolroll.web.vo.StudentScorePageAllVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:35
 * @version: 1.0
 * @description:
 */
public interface StudentScoreService {
    /**
     * 查询学生的有效课程成绩信息
     *
     * @param studentId
     * @param courseId
     * @return
     */
    StudentScore findByStudentIdAndCourseId(String studentId, String courseId);

    /**
     * 查询对应的学生成绩信息
     *
     * @param studentId
     * @return
     */
    List<StudentScore> findByStudentId(String studentId);

    /**
     * 删除对应的学生成绩信息
     *
     * @param scoreId
     */
    void deleteStudentScoreById(String scoreId);

    Page<StudentScore> findStudentScorePageAll(StudentScorePageAllVo pageAllVo, PageRequest of);

    void updateOffLineScore(OffLineScoreUpdateVo vo);

    StudentScore findById(String scoreId);

    void saveAll(List<StudentScore> list);

    List<List<String>> exportScore(String centerId);
}
