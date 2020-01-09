package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainCourse;
import com.project.train.repository.TrainCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * 财务类型明细
 */

@Slf4j
@Service
public class TrainCourseService extends BaseMySqlService {

    @Resource
    private TrainCourseRepository trainCourseRepository;


    /**
     * 财务类型明细添加
     */
    @Transactional(rollbackFor = Exception.class)
    public TrainCourse save(TrainCourse trainCourse) {
        MyAssert.isTrue(trainCourseRepository.existsByCourseName(trainCourse.getCourseName()), DefineCode.ERR0010, "已经存在对应课程名称");
        trainCourse.setCourseId(IdUtil.fastSimpleUUID());
        return trainCourseRepository.save(trainCourse);
    }

    /**
     * 财务类型明细修改
     */
    @Transactional(rollbackFor = Exception.class)
    public TrainCourse update(TrainCourse trainCourse) {
        TrainCourse obj = findId(trainCourse.getCourseId());
        if (!obj.getCourseName().equals(trainCourse.getCourseName())){
            MyAssert.isTrue(trainCourseRepository.existsByCourseName(trainCourse.getCourseName()), DefineCode.ERR0010, "已经存在对应课程名称");
        }
        BeanUtil.copyProperties(trainCourse, obj);
        return trainCourseRepository.save(obj);
    }


    /**
     * 财务类型明细BYID
     *
     * @param courseId
     * @return
     */
    public TrainCourse findId(String courseId) {
        Optional<TrainCourse> obj = trainCourseRepository.findById(courseId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 财务类型明细，不分页
     * @return
     */
    public List<TrainCourse> findAll(String centerAreaId) {
        return trainCourseRepository.findAllByCenterAreaIdOrderByCreateTimeDesc(centerAreaId);
    }

    public Page<TrainCourse> findAllPage(Pageable pageable){
        return trainCourseRepository.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
    }
}
