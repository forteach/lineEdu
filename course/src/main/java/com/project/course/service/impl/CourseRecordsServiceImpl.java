package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.project.course.domain.record.ChapterRecords;
import com.project.course.domain.record.CourseRecords;
import com.project.course.repository.CourseStudyRepository;
import com.project.course.repository.record.ChapterRecordsRepository;
import com.project.course.repository.record.CourseRecordsRepository;
import com.project.course.service.CourseRecordsService;
import com.project.course.web.req.CourseRecordsSaveReq;
import com.project.mongodb.repository.BigQuestionAnswerRepository;
import com.project.mongodb.repository.QuestionListsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final CourseStudyRepository courseStudyRepository;
    private final BigQuestionAnswerRepository bigQuestionAnswerRepository;
    private final QuestionListsRepository questionListsRepository;

    public CourseRecordsServiceImpl(CourseRecordsRepository courseRecordsRepository,
                                    CourseStudyRepository courseStudyRepository,
                                    QuestionListsRepository questionListsRepository,
                                    BigQuestionAnswerRepository bigQuestionAnswerRepository,
                                    ChapterRecordsRepository chapterRecordsRepository) {
        this.courseRecordsRepository = courseRecordsRepository;
        this.chapterRecordsRepository = chapterRecordsRepository;
        this.courseStudyRepository = courseStudyRepository;
        this.questionListsRepository = questionListsRepository;
        this.bigQuestionAnswerRepository = bigQuestionAnswerRepository;
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
        int sumLength = req.getDuration() > chapterRecords.getVideoDuration() ? chapterRecords.getVideoDuration() : req.getDuration();
        chapterRecords.setSumTime(sumLength);
        chapterRecords.setUpdateUser(req.getCreateUser());
        chapterRecordsRepository.save(chapterRecords);
    }

    @Override
    public ChapterRecords findChapterRecordsByStudentIdAndChapterId(String studentId, String courseId, String chapterId) {
        List<ChapterRecords> list = chapterRecordsRepository.findByStudentIdAndCourseIdAndChapterId(studentId, courseId, chapterId);
        if (list.isEmpty()) {
            return new ChapterRecords();
        }
        return list.get(0);
    }


    @Override
    public void taskCourseRecordsSum() {
        List<CourseRecords> list = courseRecordsRepository
                .findAllByIsValidatedEqualsAndCreateTimeAfter(TAKE_EFFECT_OPEN, DateUtil.formatDateTime(DateUtil.offset(new Date(), DateField.YEAR, -3)))
                .parallelStream()
                .filter(Objects::nonNull)
                .map(this::sumRecordsByCourseIdAndStudent)
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            courseRecordsRepository.saveAll(list);
        }
    }

    /**
     * 计算每名学生每课上课总时长
     */
    private CourseRecords sumRecordsByCourseIdAndStudent(CourseRecords courseRecords) {
        Integer sumTime = chapterRecordsRepository.findAllByIsValidatedEqualsAndCourseIdAndStudentId(TAKE_EFFECT_OPEN, courseRecords.getCourseId(), courseRecords.getStudentId())
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(ChapterRecords::getSumTime)
                .sum();
        courseRecords.setSumTime(sumTime);
        return courseRecords;
    }

    @Override
    public CourseRecords findCourseRecordsByStudentIdAndCourseId(String studentId, String courseId) {
        return courseRecordsRepository.findByIsValidatedEqualsAndStudentIdAndCourseId(TAKE_EFFECT_OPEN, studentId, courseId).orElseGet(CourseRecords::new);
    }

    @Override
    public Page<CourseRecords> findCourseByStudentId(String studentId, PageRequest page) {
        return courseRecordsRepository.findByIsValidatedEqualsAndStudentIdOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, studentId, page);
    }

    @Override
    public Page<ChapterRecords> findCourseByCourseIdAndStudentId(String studentId, String courseId, PageRequest page) {
        return chapterRecordsRepository.findByIsValidatedEqualsAndStudentIdAndCourseIdOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, studentId, courseId, page);
    }


    @Override
    public Page<CourseRecords> findCourseByCourseId(String courseId, PageRequest page) {
        String createTime = DateUtil.formatDateTime(DateUtil.offset(new Date(), DateField.YEAR, -3));
        if (StrUtil.isNotBlank(courseId)) {
            return courseRecordsRepository.findAllByIsValidatedEqualsAndCourseIdAndCreateTimeAfterOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, createTime, courseId, page);
        } else {
            return courseRecordsRepository.findAllByIsValidatedEqualsAndCreateTimeAfterOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, createTime, page);
        }
    }

    @Override
    public Page<CourseRecords> findCourseByCenterAreaId(String courseId, String centerAreaId, PageRequest page) {
        String createTime = DateUtil.formatDateTime(DateUtil.offset(new Date(), DateField.YEAR, -3));
        if (StrUtil.isNotBlank(courseId)) {
            return courseRecordsRepository.findAllByIsValidatedEqualsAndCenterAreaIdAndCourseIdAndCreateTimeAfterOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, createTime, courseId, page);
        } else {
            return courseRecordsRepository.findAllByIsValidatedEqualsAndCenterAreaIdAndCreateTimeAfterOrderByUpdateTimeDesc(TAKE_EFFECT_OPEN, centerAreaId, createTime, page);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByStudentId(String studentId) {
        courseStudyRepository.deleteAllByStudentId(studentId);
        courseRecordsRepository.deleteAllByStudentId(studentId);
        chapterRecordsRepository.deleteAllByStudentId(studentId);
        questionListsRepository.deleteAllByStudentId(studentId);
        bigQuestionAnswerRepository.deleteAllByStudentId(studentId);
    }
}