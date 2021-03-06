package com.project.databank.service.imp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.databank.domain.verify.CourseVerifyVo;
import com.project.databank.repository.verify.CourseVerifyVoRepository;
import com.project.databank.service.CourseVerifyVoService;
import com.project.databank.web.vo.CourseVerifyRequest;
import com.project.mongodb.domain.BigQuestion;
import com.project.mongodb.repository.BigQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.project.base.common.keyword.Dic.*;
import static com.project.databank.domain.verify.CourseVerifyEnum.CHAPTER_QUESTION;
import static com.project.databank.domain.verify.CourseVerifyEnum.COURSE_CHAPTER_QUESTION;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 14:40
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class CourseVerifyVoServiceImpl implements CourseVerifyVoService {
    private final CourseVerifyVoRepository courseVerifyVoRepository;
    private final RedisTemplate redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final BigQuestionRepository bigQuestionRepository;

    @Autowired
    public CourseVerifyVoServiceImpl(CourseVerifyVoRepository courseVerifyVoRepository, RedisTemplate redisTemplate,
                                     HashOperations<String, String, String> hashOperations, BigQuestionRepository bigQuestionRepository) {
        this.courseVerifyVoRepository = courseVerifyVoRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
        this.bigQuestionRepository = bigQuestionRepository;
    }

    @Override
    public Page<CourseVerifyVo> findAllPage(PageRequest pageRequest) {
        return courseVerifyVoRepository.findAllByIsValidatedEqualsAndVerifyStatusOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, VERIFY_STATUS_APPLY, pageRequest);
    }

    @Override
    public Page<CourseVerifyVo> findAllPage(String courseId, PageRequest pageRequest) {
        return courseVerifyVoRepository.findAllByIsValidatedEqualsAndVerifyStatusAndCourseIdOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, VERIFY_STATUS_APPLY, courseId, pageRequest);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(CourseVerifyVo verifyVo) {
        courseVerifyVoRepository.save(verifyVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CourseVerifyVo verifyVo) {
        courseVerifyVoRepository.save(verifyVo);
    }

    @Async
    @Override
    public void saveAll(List<CourseVerifyVo> verifyVoList) {
        courseVerifyVoRepository.saveAll(verifyVoList);
    }

    @Override
    public Optional<CourseVerifyVo> findById(String id) {
        return courseVerifyVoRepository.findById(id);
    }

    @Override
    @SuppressWarnings(value = "all")
    @Transactional(rollbackFor = Exception.class)
    public void taskQuestionRedis() {
        Set<String> set = redisTemplate.opsForSet().members(QUESTIONS_VERIFY);
        List<CourseVerifyVo> list = set.stream()
                .filter(Objects::nonNull)
                .map(questionId -> {
                    Map<String, String> map = hashOperations.entries(QUESTION_CHAPTER.concat(questionId));
                    if (MapUtil.isNotEmpty(map)) {
                        CourseVerifyVo verifyVo = BeanUtil.mapToBean(map, CourseVerifyVo.class, true);
                        verifyVo.setSubmitType("添加修改习题");
                        verifyVo.setCourseType(COURSE_CHAPTER_QUESTION.getValue());
//                        verifyVo.setUpdateUser(verifyVo.getTeacherId());
//                        verifyVo.setCreateUser(verifyVo.getTeacherId());
                        verifyVo.setVerifyStatus(VERIFY_STATUS_APPLY);
                        verifyVo.setDatumType(CHAPTER_QUESTION.getValue());
                        verifyVo.setFileName(map.get("choiceQstTxt"));
                        return verifyVo;
                    }
                    //删除对应的键值信息
                    redisTemplate.opsForSet().remove(QUESTIONS_VERIFY, questionId);
                    redisTemplate.delete(QUESTION_CHAPTER.concat(questionId));
                    return null;
                })
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            courseVerifyVoRepository.saveAll(list);
        }
        list.forEach(c -> {
            //删除对应的键值信息
            redisTemplate.opsForSet().remove(QUESTIONS_VERIFY, c.getQuestionId());
            redisTemplate.delete(QUESTION_CHAPTER.concat(c.getQuestionId()));
        });
    }

    @Override
    public void verifyQuestion(CourseVerifyRequest request) {
        Optional<BigQuestion> optional = bigQuestionRepository.findById(request.getId());
        MyAssert.isFalse(optional.isPresent(), DefineCode.ERR0014, "不存在要审核的题目信息");
        BigQuestion bigQuestion = optional.get();
        bigQuestion.setVerifyStatus(request.getVerifyStatus());
        bigQuestion.setRemark(request.getRemark());
        BigQuestion result = bigQuestionRepository.save(bigQuestion);
        MyAssert.isNull(result, DefineCode.ERR0013, "操作失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByCourseIdAndChapterIdAndVerifyStatusAndCourseType(String courseId, String chapterId, String verifyStatus, String courseType) {
        courseVerifyVoRepository.deleteAllByCourseIdAndChapterIdAndVerifyStatusAndCourseType(courseId, chapterId, verifyStatus, courseType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByCourseIdAndChapterId(String courseId, String chapterId){
        courseVerifyVoRepository.deleteAllByCourseIdAndChapterId(courseId, chapterId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByFileId(String fileId) {
        courseVerifyVoRepository.deleteByFileId(fileId);
    }

//    @Override
//    public List<String> findVerifyCourse() {
//        return courseVerifyVoRepository.findDistinctAllByIsValidatedEqualsAndVerifyStatus();
//    }

    @Override
    public boolean existsByCourseId(String courseId){
        return courseVerifyVoRepository.existsAllByIsValidatedEqualsAndCourseIdAndVerifyStatus(TAKE_EFFECT_OPEN, courseId, VERIFY_STATUS_APPLY);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByCourseId(String courseId){
        courseVerifyVoRepository.deleteAllByCourseId(courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByCourseIdAndChapterIds(String courseId, Set<String> chapterIds){
        courseVerifyVoRepository.deleteAllByCourseIdAndChapterIdIn(courseId, chapterIds);
    }
}