package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainCourse;
import com.project.train.repository.TrainCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    public TrainCourse save(TrainCourse trainCourse) {
        trainCourse.setCourseId(IdUtil.fastSimpleUUID());
        return trainCourseRepository.save(trainCourse);
    }

    /**
     * 财务类型明细修改
     */
    public TrainCourse update(TrainCourse trainCourse) {
        TrainCourse obj = findId(trainCourse.getCourseId());
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
        return trainCourseRepository.findAllByCenterAreaId(centerAreaId);
    }
}
