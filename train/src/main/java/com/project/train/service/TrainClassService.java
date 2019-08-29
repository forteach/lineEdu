package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainClass;
import com.project.train.repository.TrainClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 项目计划班级信息记录
 */

@Slf4j
@Service
public class TrainClassService extends BaseMySqlService {

    @Resource
    private TrainClassRepository trainClassRepository;

    @Resource
    private TrainPlanFinishService trainPlanFinishService;

    /**
     * 项目计划班级添加
     */
    @Transactional
    public TrainClass save(TrainClass trainClass) {
        trainClass.setTrainClassId(IdUtil.fastSimpleUUID());
        trainClassRepository.save(trainClass);

        //判断是否全部完善信息了
        String pjPlanId=trainClass.getPjPlanId();
        trainPlanFinishService.updateAll(pjPlanId);
        return trainClass;
    }

    /**
     * 项目计划班级修改
     */
    public TrainClass update(TrainClass trainClass) {
        TrainClass obj = findId(trainClass.getTrainClassId());
        BeanUtil.copyProperties(trainClass, obj);
        return trainClassRepository.save(obj);
    }


    /**
     * 项目计划班级BYID
     *
     * @param trainClassId
     * @return
     */
    public TrainClass findId(String trainClassId) {
        Optional<TrainClass> obj = trainClassRepository.findById(trainClassId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }


    /**
     * @param pjPlanId   获取计划项目的班级列表
     * @param pageable
     * @return
     */
    public Page<TrainClass> findPlanPage(String pjPlanId, Pageable pageable) {

        return trainClassRepository.findByPjPlanIdOrderByCreateTime(pjPlanId, pageable);
    }

    /**
     * @param centerId 获取计划项目的班级列表
     * @param pageable
     * @return
     */
    public Page<TrainClass> findAllPage(String centerId, Pageable pageable) {

        return trainClassRepository.findAllByCenterAreaIdOrderByCreateTimeDesc(centerId, pageable);
    }
}
