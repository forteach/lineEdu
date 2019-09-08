package com.project.schoolroll.service.online;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.domain.online.OnLineCourse;
import com.project.schoolroll.domain.online.OnLineCourseDic;
import com.project.schoolroll.repository.online.OnLineCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * 在线课程
 */

@Slf4j
@Service
public class OnLineCourseService extends BaseMySqlService {

    private final OnLineCourseRepository onLineCourseRepository;

    @Autowired
    public OnLineCourseService(OnLineCourseRepository onLineCourseRepository) {
        this.onLineCourseRepository = onLineCourseRepository;
    }


    /**
     * 财务类型明细添加
     */
    @Transactional(rollbackFor = Exception.class)
    public OnLineCourse save(OnLineCourse onlineCourse) {
        onlineCourse.setCourseId(IdUtil.fastSimpleUUID());
        return onLineCourseRepository.save(onlineCourse);
    }

    /**
     * 财务类型明细修改
     */
    @Transactional(rollbackFor = Exception.class)
    public OnLineCourse update(OnLineCourse onlineCourse) {
        OnLineCourse obj = findId(onlineCourse.getCourseId());
        BeanUtil.copyProperties(onlineCourse, obj);
        return onLineCourseRepository.save(obj);
    }


    /**
     * 财务类型明细BYID
     *
     * @param courseId
     * @return
     */
    public OnLineCourse findId(String courseId) {
        Optional<OnLineCourse> obj = onLineCourseRepository.findById(courseId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 财务类型明细，不分页
     * @return
     */
    public List<OnLineCourse> findAllByCenterAreaId(String centerAreaId) {
        return onLineCourseRepository.findAllByIsValidatedEqualsAndCenterAreaId(TAKE_EFFECT_OPEN, centerAreaId);
    }

    public List<OnLineCourse> findAll() {
        return onLineCourseRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN);
    }

    public Page<OnLineCourse> findAllPage(PageRequest request){
        return onLineCourseRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN, request);
    }

    public Page<OnLineCourse> findAllPageByCenterAreaId(String centerAreaId, PageRequest request){
        return onLineCourseRepository.findAllByIsValidatedEqualsAndCenterAreaId(TAKE_EFFECT_OPEN, centerAreaId, request);
    }


    @Transactional(rollbackFor = Exception.class)
    public void removeByCourseId(String courseId) {
        onLineCourseRepository.findById(courseId).ifPresent(o -> {
            o.setIsValidated(TAKE_EFFECT_CLOSE);
            onLineCourseRepository.save(o);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByCourseId(String courseId) {
        onLineCourseRepository.deleteById(courseId);
    }
}