package com.project.databank.service;

import com.project.databank.domain.verify.CourseVerifyVo;
import com.project.databank.web.vo.CourseVerifyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:40
 * @version: 1.0
 * @description:
 */
public interface CourseVerifyVoService {

    void save(CourseVerifyVo verifyVo);

    void update(CourseVerifyVo verifyVo);

    Page<CourseVerifyVo> findAllPage(String courseName, PageRequest pageRequest);

    Page<CourseVerifyVo> findAllPage(PageRequest pageRequest);

    void saveAll(List<CourseVerifyVo> verifyVoList);

    Optional<CourseVerifyVo> findById(String id);

    void taskQuestionRedis();

    void deleteByFileId(String fileId);

    void verifyQuestion(CourseVerifyRequest request);

    void deleteAllByCourseIdAndChapterIdAndVerifyStatusAndCourseType(String courseId, String chapterId, String verifyStatus, String courseType);

    List<String> findVerifyCourse();

    void deleteAllByCourseId(String courseId);
}