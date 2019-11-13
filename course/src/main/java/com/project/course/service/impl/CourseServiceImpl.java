package com.project.course.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.course.domain.Course;
import com.project.course.domain.CourseImages;
import com.project.course.domain.CourseStudy;
import com.project.course.repository.CourseRepository;
import com.project.course.repository.CourseStudyRepository;
import com.project.course.repository.dto.ChapterRecordDto;
import com.project.course.repository.dto.ICourseDto;
import com.project.course.repository.dto.ICourseListDto;
import com.project.course.repository.dto.ICourseStudyDto;
import com.project.course.repository.record.CourseRecordsRepository;
import com.project.course.service.CourseService;
import com.project.course.web.req.CourseImagesReq;
import com.project.course.web.resp.CourseListResp;
import com.project.course.web.vo.CourseTeacherVo;
import com.project.course.web.vo.CourseVo;
import com.project.mongodb.domain.QuestionsLists;
import com.project.mongodb.domain.base.QuestionAnswer;
import com.project.mongodb.repository.QuestionListsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;

import static com.project.base.common.keyword.Dic.*;
import static java.util.stream.Collectors.toList;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-21 15:59
 * @Version: 1.0
 * @Description:　课程管理
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    /**
     * 课程基本信息
     */
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CourseRecordsRepository courseRecordsRepository;

    /**
     * 课程轮播图
     */
    @Resource
    private CourseImagesServiceImpl courseImagesServiceImpl;

    @Resource
    private CourseStudyRepository courseStudyRepository;
    @Resource
    private QuestionListsRepository questionListsRepository;

    /**
     * 保存课程基本信息
     *
     * @param course 课程基本信息
     *               //     * @param teachers 集体备课教师信息
     * @return 课程编号和集体备课资源编号
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public String saveUpdate(Course course) {
        //1、保存课程基本信息
        if (StrUtil.isBlank(course.getCourseId())) {
            course.setCourseId(IdUtil.fastSimpleUUID());
            return courseRepository.save(course).getCourseId();
        } else {
            courseRepository.findById(course.getCourseId()).ifPresent(c -> {
                UpdateUtil.copyProperties(course, c);
                c.setUpdateUser(course.getCreateUser());
                courseRepository.save(c);
            });
        }
        return course.getCourseId();
    }

    /**
     * 获得所有可用课程列表
     *
     * @param page
     * @return
     */
    @Override
    public List<ICourseListDto> findAll(PageRequest page) {
        return courseRepository.findByIsValidated(TAKE_EFFECT_OPEN, page).getContent();
    }

    @Override
    public Course findByCourseId(String courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    /**
     * 分页查询我的课程科目
     *
     * @param page
     * @return
     */
    @Override
    public List<ICourseListDto> findMyCourse(String userId, PageRequest page) {
        return courseRepository.findByCreateUserOrderByCreateTimeDesc(userId, page).getContent();
    }

    /**
     * 根据课程编号，获得课程基本信息
     *
     * @param id
     * @return
     */
    @Override
    public Course getById(String id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        MyAssert.isFalse(optionalCourse.isPresent(), DefineCode.ERR0010, "编号对应的课程信息不存在");
        return optionalCourse.get();
    }

    /**
     * 学生端查询我的课程信息
     *
     * @param classId
     * @return
     */
    @Override
    @Cacheable(value = "myCourseList", key = "#classId", sync = true, unless = "#result eq null")
    public List<CourseListResp> myCourseList(String classId) {
        List<CourseListResp> listRespList = CollUtil.newArrayList();
        courseRepository.findByIsValidatedEqualsAndCourseIdInOrderByCreateTime(classId)
                .stream()
                .forEach(iCourseChapterListDto -> {
                    listRespList.add(CourseListResp.builder()
                            .courseId(iCourseChapterListDto.getCourseId())
                            .courseName(iCourseChapterListDto.getCourseName())
                            .alias(iCourseChapterListDto.getAlias())
                            .topPicSrc(iCourseChapterListDto.getTopPicSrc())
                            .courseDescribe(iCourseChapterListDto.getCourseDescribe())
                            .joinChapterId(iCourseChapterListDto.getChapterId())
                            .joinChapterName(iCourseChapterListDto.getChapterName())
                            .teacherId(iCourseChapterListDto.getTeacherId())
                            .teacherName(iCourseChapterListDto.getTeacherName())
                            .build());
                });
        return listRespList;
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void updateStatusById(String courseId, String userId) {
        courseRepository.findById(courseId)
                .ifPresent(course -> {
                    if (TAKE_EFFECT_CLOSE.equals(course.getIsValidated())){
                        course.setIsValidated(TAKE_EFFECT_OPEN);
                    }else {
                        course.setIsValidated(TAKE_EFFECT_CLOSE);
                    }
                    course.setUpdateUser(userId);
                    courseRepository.save(course);
                });
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void saveCourseImages(CourseImagesReq courseImagesReq) {
        courseImagesServiceImpl.saveCourseImages(courseImagesReq.getCourseId(), courseImagesReq.getImages(),
                courseImagesReq.getCreateUser(), courseImagesReq.getCenterAreaId());
    }


    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void deleteById(String courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    /**
     * 查询封面图片信息
     *
     * @param courseId
     * @return
     */
    @Override
    public List<CourseImages> findImagesByCourseId(String courseId, String verifyStatus) {
        return courseImagesServiceImpl.findImagesByCourseId(courseId, verifyStatus);
    }

    @Override
    public List<ICourseStudyDto> findCourseStudyList(String studentId, Integer studyStatus) {
        return courseStudyRepository.findByIsValidatedEqualsAndStudentId(studentId, studyStatus);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteImagesByCourseId(String courseId) {
        return courseImagesServiceImpl.deleteImagesByCourseId(courseId);
    }

    @Override
    public List<CourseVo> findByCourseNumberAndTeacherId(List<CourseTeacherVo> courseIds, String classId, String userId) {
        List<CourseVo> vos = new ArrayList<>();
        for (CourseTeacherVo v : courseIds) {
            List<ICourseDto> list = courseRepository.findAllByCourseNumberAndCreateUserOrderByCreateTimeDescDto(v.getCourseId(), v.getTeacherId());
            for (ICourseDto iCourseDto: list) {
                String chapterId = null;
                String chapterName = null;
                ChapterRecordDto dto = courseRecordsRepository.findDtoByStudentIdAndCourseId(userId, iCourseDto.getCourseId());
                if (dto != null) {
                    chapterId = dto.getChapterId();
                    chapterName = dto.getChapterName();
                }
                vos.add(new CourseVo(iCourseDto.getCourseId(), iCourseDto.getCourseName(), iCourseDto.getCourseNumber(), iCourseDto.getAlias(),
                        iCourseDto.getTopPicSrc(), iCourseDto.getCourseDescribe(), iCourseDto.getLearningTime(), iCourseDto.getVideoPercentage(),
                        iCourseDto.getJobsPercentage(), iCourseDto.getCreateUser(), iCourseDto.getCreateUserName(), chapterId, chapterName));
            }
        }
        stringRedisTemplate.opsForValue().set(TEACH_PLAN_CLASS_COURSEVO.concat(classId), JSONUtil.toJsonStr(vos), Duration.ofSeconds(10));
        return vos;
    }

    @Override
    public List<CourseVo> findCourseVoByClassId(String classId) {
        String key = TEACH_PLAN_CLASS_COURSEVO.concat(classId);
        if (stringRedisTemplate.hasKey(key)) {
            return JSONUtil.toList(JSONUtil.parseArray(stringRedisTemplate.opsForValue().get(key)), CourseVo.class);
        }
        return null;
    }
    @Override
    public List<Course> findAllCourseVoByCreateUser(String createUser){
        return courseRepository.findAllByCreateUserOrderByCreateTimeDesc(createUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCourseTime(String courseId, Integer videoTimeNum) {
        courseRepository.findById(courseId).ifPresent(c -> c.setVideoTimeNum(videoTimeNum));
    }

    @Override
    public void taskCourseStudy() {
        courseRecordsRepository.findAllByIsValidatedEqualsAndCreateTimeAfter(TAKE_EFFECT_OPEN, DateUtil.formatDateTime(DateUtil.offset(new Date(), DateField.YEAR, -1)))
                .parallelStream()
                .forEach(r -> courseRepository.findById(r.getCourseId())
                        .ifPresent(c -> {
                            CourseStudy courseStudy = findCourseStudy(c.getCourseId(), r.getStudentId());
                            courseStudy.setStudentId(r.getStudentId());
                            courseStudy.setCourseId(c.getCourseId());
                            courseStudy.setOnLineTime(r.getSumTime());
                            courseStudy.setOnLineTimeSum(c.getVideoTimeNum());
                            courseStudyRepository.save(courseStudy);
                        })
                );
    }

    private CourseStudy findCourseStudy(String courseId, String studentId) {
        return courseStudyRepository.findAllByCourseIdAndStudentId(courseId, studentId).orElseGet(CourseStudy::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void taskCourseQuestions() {
        List<CourseStudy> list = courseStudyRepository.findAllByIsValidatedEqualsAndCreateTimeAfter(TAKE_EFFECT_OPEN, DateUtil.formatDateTime(DateUtil.offset(new Date(), DateField.YEAR, -1)))
                .parallelStream()
                .filter(Objects::nonNull)
                .map(this::setStudyValue)
                .collect(toList());
        courseStudyRepository.saveAll(list);
    }

    /**
     * 查询计算每个学生每门课回答的习题数量和回答正确的习题数量
     *
     * @return 成绩统计信息
     */
    private CourseStudy setStudyValue(CourseStudy courseStudy) {
        // 查询课程学生对应的习题信息
        List<QuestionsLists> list = questionListsRepository.findAllByCourseIdAndStudentId(courseStudy.getCourseId(), courseStudy.getStudentId());
        //计算全部回答习题数量
        int answerSum = list.stream().filter(Objects::nonNull).map(QuestionsLists::getQuestionIds).filter(Objects::nonNull).mapToInt(List::size).sum();
        //过滤回答正确的习题数量
        long correctSum = list.stream()
                .map(QuestionsLists::getBigQuestions)
                .filter(Objects::nonNull)
                .mapToLong(this::countRightQuestion)
                .sum();
        courseStudy.setCorrectSum((int) correctSum);
        courseStudy.setAnswerSum(answerSum);
        return courseStudy;
    }

    /**
     * @param questionAnswers 回答的习题列表
     * @return 正确的题目数量
     * @description 过虑正确题数量
     */
    private long countRightQuestion(List<QuestionAnswer> questionAnswers) {
        return questionAnswers.stream()
                .map(QuestionAnswer::getRight)
                .filter(Objects::nonNull)
                .filter(b -> b)
                .count();
    }

    @Override
    public Page<ICourseStudyDto> findCourseStudyPageAll(String courseId, String studentId, PageRequest pageRequest) {
        if (StrUtil.isBlank(studentId)) {
            return courseStudyRepository.findAllByIsValidatedEqualsAndCourseIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, courseId, pageRequest);
        }
        return courseStudyRepository.findAllByIsValidatedEqualsAndCourseIdAndStudentIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, courseId, studentId, pageRequest);
    }
}