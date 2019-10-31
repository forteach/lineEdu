package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.project.course.domain.record.ChapterRecords;
import com.project.course.domain.record.CourseRecords;
import com.project.course.repository.record.ChapterRecordsRepository;
import com.project.course.repository.record.CourseRecordsRepository;
import com.project.course.service.CourseRecordsService;
import com.project.course.web.req.CourseRecordsSaveReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 11:02
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class CourseRecordsServiceImpl implements CourseRecordsService {
    private final CourseRecordsRepository courseRecordsRepository;
    private final ChapterRecordsRepository chapterRecordsRepository;

    public CourseRecordsServiceImpl(CourseRecordsRepository courseRecordsRepository, ChapterRecordsRepository chapterRecordsRepository) {
        this.courseRecordsRepository = courseRecordsRepository;
        this.chapterRecordsRepository = chapterRecordsRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCourseRecord(CourseRecordsSaveReq req) {
        CourseRecords courseRecords = findCourseRecordsByStudentIdAndCourseId(req.getStudentId(), req.getCourseId());
        BeanUtil.copyProperties(req, courseRecords);
        courseRecords.setUpdateUser(req.getCreateUser());
        courseRecordsRepository.save(courseRecords);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveChapterRecord(CourseRecordsSaveReq req) {
        ChapterRecords chapterRecords = findChapterRecordsByStudentIdAndChapterId(req.getStudentId(), req.getCourseId(), req.getChapterId());
        BeanUtil.copyProperties(req, chapterRecords);
        long length = req.getDuration() + chapterRecords.getSumTime();
        long sumLength = length > req.getVideoDuration() ? req.getVideoDuration() : length;
        chapterRecords.setSumTime(sumLength);
        chapterRecords.setUpdateUser(req.getCreateUser());
        chapterRecordsRepository.save(chapterRecords);
    }

    @Override
    public ChapterRecords findChapterRecordsByStudentIdAndChapterId(String studentId, String courseId, String chapterId) {
        return chapterRecordsRepository.findByIsValidatedEqualsAndStudentIdAndCourseIdAndChapterId(TAKE_EFFECT_OPEN, studentId, courseId, chapterId)
                .orElseGet(ChapterRecords::new);
    }

    @Override
    public CourseRecords findCourseRecordsByStudentIdAndCourseId(String studentId, String courseId) {
        return courseRecordsRepository.findByIsValidatedEqualsAndStudentIdAndCourseId(TAKE_EFFECT_OPEN, studentId, courseId)
                .orElseGet(CourseRecords::new);
    }

    @Override
    public Page<CourseRecords> findCourseByStudentId(String studentId, int page, int size) {
        return courseRecordsRepository.findByIsValidatedEqualsAndStudentIdOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, studentId, PageRequest.of(page, size));
    }

    @Override
    public Page<ChapterRecords> findCourseByCourseIdAndStudentId(String studentId, String courseId, int page, int size) {
        return chapterRecordsRepository.findByIsValidatedEqualsAndStudentIdAndCourseIdOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, studentId, courseId, PageRequest.of(page, size));
    }
}
