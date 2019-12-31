package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.OnLineCourseDic;
import com.project.course.repository.OnLineCourseDicRepository;
import com.project.course.service.OnLineCourseDicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.*;

/**
 * 财务类型明细
 */

@Slf4j
@Service
public class OnLineCourseDicServiceImpl implements OnLineCourseDicService {

    private final OnLineCourseDicRepository onLineCourseDicRepository;

    @Autowired
    public OnLineCourseDicServiceImpl(OnLineCourseDicRepository onLineCourseDicRepository) {
        this.onLineCourseDicRepository = onLineCourseDicRepository;
    }


    /**
     * 财务类型明细添加
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OnLineCourseDic save(OnLineCourseDic onLineCourseDic) {
        existsAllCourseNameAndType(onLineCourseDic.getCourseName(), onLineCourseDic.getType());
        onLineCourseDic.setCourseId(IdUtil.fastSimpleUUID());
        return onLineCourseDicRepository.save(onLineCourseDic);
    }

    private void existsAllCourseNameAndType(String courseName, String type) {
        MyAssert.isTrue(onLineCourseDicRepository.existsAllByCourseNameAndType(courseName, type), DefineCode.ERR0013, "已经存相同名称和类型的课程信息");
    }

    /**
     * 财务类型明细修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OnLineCourseDic update(OnLineCourseDic onLineCourseDic) {
        OnLineCourseDic obj = findId(onLineCourseDic.getCourseId());
        if (StrUtil.isNotBlank(onLineCourseDic.getCourseName()) && !obj.getCourseName().equals(onLineCourseDic.getCourseName())){
            existsAllCourseNameAndType(onLineCourseDic.getCourseName(), obj.getType());
        }
        if (StrUtil.isNotBlank(onLineCourseDic.getType()) && !obj.getType().equals(onLineCourseDic.getType())) {
            existsAllCourseNameAndType(obj.getCourseName(), onLineCourseDic.getType());
        }
        BeanUtil.copyProperties(onLineCourseDic, obj);
        return onLineCourseDicRepository.save(obj);
    }


    /**
     * 财务类型明细BYID
     *
     * @param courseId
     * @return
     */
    @Override
    public OnLineCourseDic findId(String courseId) {
        Optional<OnLineCourseDic> obj = onLineCourseDicRepository.findById(courseId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "不存在对应的课程信息");
        return obj.get();
    }

    /**
     * @param centerAreaId 财务类型明细，不分页
     * @return
     */
    @Override
    public List<OnLineCourseDic> findAllByCenterAreaId(String centerAreaId) {
        return onLineCourseDicRepository.findAllByIsValidatedEqualsAndCenterAreaId(TAKE_EFFECT_OPEN, centerAreaId);
    }

    @Override
    public List<OnLineCourseDic> findAll() {
        return onLineCourseDicRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByCourseId(String courseId) {
        onLineCourseDicRepository.findById(courseId).ifPresent(o -> {
            o.setIsValidated(TAKE_EFFECT_CLOSE);
            onLineCourseDicRepository.save(o);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCourseId(String courseId) {
        onLineCourseDicRepository.deleteById(courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String courseId, String userId) {
        Optional<OnLineCourseDic> optional = onLineCourseDicRepository.findById(courseId);
        if (optional.isPresent()) {
            optional.ifPresent(o -> {
                String status = o.getIsValidated();
                if (TAKE_EFFECT_CLOSE.equals(status)) {
                    o.setIsValidated(TAKE_EFFECT_OPEN);
                } else {
                    o.setIsValidated(TAKE_EFFECT_CLOSE);
                }
                o.setUpdateUser(userId);
                onLineCourseDicRepository.save(o);
            });
        } else {
            MyAssert.isNull(null, DefineCode.ERR0014, "不存在要修改的信息");
        }
    }

    @Override
    public Page<OnLineCourseDic> findAllPage(PageRequest pageRequest, String courseName) {
        if (StrUtil.isNotBlank(courseName)){
            return onLineCourseDicRepository.findAllByCourseNameContaining(courseName, pageRequest);
        }
        return onLineCourseDicRepository.findAll(pageRequest);
    }

    @Override
    public Page<OnLineCourseDic> findAllPageByType(PageRequest pageRequest, String type, String courseName) {
        if (COURSE_TYPE_4.equals(type)) {
            List<String> list = CollUtil.toList(COURSE_TYPE_1, COURSE_TYPE_3);
            if (StrUtil.isNotBlank(courseName)){
                return onLineCourseDicRepository.findAllByIsValidatedEqualsAndTypeInAndCourseNameContaining(TAKE_EFFECT_OPEN, list, courseName, pageRequest);
            }
            return onLineCourseDicRepository.findAllByIsValidatedEqualsAndTypeIn(TAKE_EFFECT_OPEN, list, pageRequest);
        }
        if (StrUtil.isNotBlank(courseName)){
            return onLineCourseDicRepository.findAllByIsValidatedEqualsAndTypeAndCourseNameContaining(TAKE_EFFECT_OPEN, type, courseName, pageRequest);
        }
        return onLineCourseDicRepository.findAllByIsValidatedEqualsAndType(TAKE_EFFECT_OPEN, type, pageRequest);
    }
}