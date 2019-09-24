package com.project.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.OnLineCourseDic;
import com.project.course.repository.OnLineCourseDicRepository;
import com.project.course.service.OnLineCourseDicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

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
        Optional<OnLineCourseDic> optional = findByCourseName(onLineCourseDic.getCourseName());
        if (optional.isPresent()) {
            MyAssert.isNull(null, DefineCode.ERR0013, "已经存同名的课程信息");
        }
        onLineCourseDic.setCourseId(IdUtil.fastSimpleUUID());
        return onLineCourseDicRepository.save(onLineCourseDic);
    }

    private Optional<OnLineCourseDic> findByCourseName(String courseName) {
        return onLineCourseDicRepository.findByCourseName(courseName);
    }

    /**
     * 财务类型明细修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OnLineCourseDic update(OnLineCourseDic onLineCourseDic) {
        OnLineCourseDic obj = findId(onLineCourseDic.getCourseId());
        if (StrUtil.isNotBlank(onLineCourseDic.getCourseName()) && !obj.getCourseName().equals(onLineCourseDic.getCourseName())) {
            Optional<OnLineCourseDic> optional = findByCourseName(onLineCourseDic.getCourseName());
            if (optional.isPresent()) {
                MyAssert.isNull(null, DefineCode.ERR0013, "已经存同名的课程信息");
            }
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
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
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
}
