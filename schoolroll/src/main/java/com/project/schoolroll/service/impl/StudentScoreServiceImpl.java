package com.project.schoolroll.service.impl;

import com.project.schoolroll.domain.StudentScore;
import com.project.schoolroll.repository.StudentScoreRepository;
import com.project.schoolroll.service.StudentScoreService;
import com.project.schoolroll.web.vo.StudentScorePageAllVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:38
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class StudentScoreServiceImpl implements StudentScoreService {
    private final StudentScoreRepository studentScoreRepository;

    public StudentScoreServiceImpl(StudentScoreRepository studentScoreRepository) {
        this.studentScoreRepository = studentScoreRepository;
    }

    @Override
    public StudentScore findByStudentIdAndCourseId(String studentId, String courseId) {
        return studentScoreRepository.findAllByIsValidatedEqualsAndStuIdAndCourseId(TAKE_EFFECT_OPEN, studentId, courseId);
    }

    @Override
    public List<StudentScore> findByStudentId(String studentId) {
        return studentScoreRepository.findAllByIsValidatedEqualsAndStuIdOrderByUpdateTime(TAKE_EFFECT_OPEN, studentId);
    }

    @Override
    public void deleteStudentScoreById(String scoreId) {
        studentScoreRepository.deleteById(scoreId);
    }

    @Override
    public Page<?> findStudentScorePageAll(StudentScorePageAllVo pageAllVo, PageRequest of) {
        return null;
    }
}
