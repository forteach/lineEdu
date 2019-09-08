package com.project.schoolroll.service.online;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.schoolroll.domain.online.OnLineCourseDic;
import com.project.schoolroll.repository.online.OnLineCourseDicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_CLOSE;
import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * 财务类型明细
 */

@Slf4j
@Service
public class OnLineCourseService extends BaseMySqlService {

    @Resource
    private OnLineCourseDicRepository onLineCourseDicRepository;


    /**
     * 财务类型明细添加
     */
    public OnLineCourseDic save(OnLineCourseDic OnlineCourse) {
        OnlineCourse.setCourseId(IdUtil.fastSimpleUUID());
        return onLineCourseDicRepository.save(OnlineCourse);
    }

    /**
     * 财务类型明细修改
     */
    public OnLineCourseDic update(OnLineCourseDic OnlineCourse) {
        OnLineCourseDic obj = findId(OnlineCourse.getCourseId());
        BeanUtil.copyProperties(OnlineCourse, obj);
        return onLineCourseDicRepository.save(obj);
    }


    /**
     * 财务类型明细BYID
     *
     * @param courseId
     * @return
     */
    public OnLineCourseDic findId(String courseId) {
        Optional<OnLineCourseDic> obj = onLineCourseDicRepository.findById(courseId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 财务类型明细，不分页
     * @return
     */
    public List<OnLineCourseDic> findAllByCenterAreaId(String centerAreaId) {
        return onLineCourseDicRepository.findAllByIsValidatedEqualsAndCenterAreaId(TAKE_EFFECT_OPEN, centerAreaId);
    }

    public List<OnLineCourseDic> findAll() {
        return onLineCourseDicRepository.findAllByIsValidatedEquals(TAKE_EFFECT_OPEN);
    }

    public void removeByCourseId(String courseId) {
        onLineCourseDicRepository.findById(courseId).ifPresent(o -> {
            o.setIsValidated(TAKE_EFFECT_CLOSE);
            onLineCourseDicRepository.save(o);
        });
    }

    public void deleteByCourseId(String courseId) {
        onLineCourseDicRepository.deleteById(courseId);
    }
}
