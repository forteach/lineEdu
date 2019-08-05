package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
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

import java.util.Optional;

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
    public void saveRecords(CourseRecordsSaveReq req) {
        Optional<ChapterRecords> optional = chapterRecordsRepository
                .findByIsValidatedEqualsAndStudentIdAndCourseIdAndChapterId(TAKE_EFFECT_OPEN, req.getStudentId(), req.getCourseId(), req.getChapterId());
        if (optional.isPresent()) {
            ChapterRecords chapterRecords = optional.get();
            chapterRecords.setLocationTime(req.getLocationTime());
            //判断观看总时长最大是视频长度
            long sumTime = chapterRecords.getSumTime() + req.getDuration();
            chapterRecords.setSumTime(sumTime < req.getVideoDuration() ? sumTime : req.getVideoDuration());
            chapterRecordsRepository.save(chapterRecords);
            saveCourseRecords(req.getStudentId(), req.getCourseId(), req.getDuration());
        }else {
            ChapterRecords chapterRecords = new ChapterRecords();
            BeanUtil.copyProperties(req, chapterRecords);
            chapterRecords.setSumTime(req.getDuration() < req.getVideoDuration() ? req.getDuration() : req.getVideoDuration());
            chapterRecords.setRecordId(IdUtil.fastSimpleUUID());
            chapterRecordsRepository.save(chapterRecords);
            saveCourseRecords(req.getStudentId(), req.getCourseId(), req.getDuration());
        }
    }

    /**
     * 保存课程记录
     * @param studentId
     * @param courseId
     * @param duration
     */
    private void saveCourseRecords(String studentId, String courseId, int duration){
        Optional<CourseRecords> optional = courseRecordsRepository.findByIsValidatedEqualsAndStudentIdAndCourseId(TAKE_EFFECT_OPEN, studentId, courseId);
        if (optional.isPresent()){
            CourseRecords records = optional.get();
            records.setSumTime(records.getSumTime() + duration);
            courseRecordsRepository.save(records);
        }else {
            CourseRecords courseRecords = new CourseRecords();
            courseRecords.setSumTime(duration);
            courseRecords.setRecordId(IdUtil.fastSimpleUUID());
            courseRecords.setStudentId(studentId);
            courseRecords.setCourseId(courseId);
            courseRecordsRepository.save(courseRecords);
        }
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
