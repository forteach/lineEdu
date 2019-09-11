package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainPlanFinish;
import com.project.train.domain.TrainProjectPlan;
import com.project.train.repository.TrainProjectPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 项目计划信息记录
 */

@Slf4j
@Service
public class TrainPlanService extends BaseMySqlService {

    @Resource
    private TrainProjectPlanRepository trainProjectPlanRepository;

    @Resource
    private TrainPlanFinishService trainPlanFinishService;

    /**
     * 项目计划添加
     */
    @Transactional(rollbackFor = Exception.class)
    public TrainProjectPlan save(TrainProjectPlan trainProjectPlan) {
        trainProjectPlan.setPjPlanId(IdUtil.fastSimpleUUID());
        trainProjectPlanRepository.save(trainProjectPlan);

        TrainPlanFinish tf= new TrainPlanFinish();
        tf.setPjPlanId(trainProjectPlan.getPjPlanId());
        tf.setCenterAreaId(trainProjectPlan.getCenterAreaId());
        tf.setTrainProjectName(trainProjectPlan.getTrainProjectName());
        trainPlanFinishService.save(tf);
        return trainProjectPlan;
    }

    /**
     * 项目计划添加
     */
    @Transactional(rollbackFor = Exception.class)
    public TrainProjectPlan update(TrainProjectPlan trainProjectPlan) {
        TrainProjectPlan obj = findId(trainProjectPlan.getPjPlanId());
        BeanUtil.copyProperties(trainProjectPlan, obj);
        return trainProjectPlanRepository.save(obj);
    }


    /**
     * 项目计划BYID
     *
     * @param pjPlanId
     * @return
     */
    public TrainProjectPlan findId(String pjPlanId) {
        Optional<TrainProjectPlan> obj = trainProjectPlanRepository.findById(pjPlanId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014, "未找到该条记录");
        return obj.get();
    }

    /**
     * @param centerAreaId 获取前多少天项目计划列表
     * @param agoDay       前多少天
     * @param pageable
     * @return
     */
    public Page<TrainProjectPlan> findAllPage(String centerAreaId, int agoDay, Pageable pageable) {
        //提前天数的日期
        String fromDay = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -agoDay));
        //当前日期
        return trainProjectPlanRepository.findAllByCenterAreaIdAndTrainStartAfterOrderByTrainStartDesc(centerAreaId, fromDay, pageable);
    }

    /**
     * @param centerAreaId 获取所有项目计划列表
     * @param pageable
     * @return
     */
    public Page<TrainProjectPlan> findAllPage(String centerAreaId, Pageable pageable) {

        return trainProjectPlanRepository.findAllByCenterAreaIdOrderByTrainStartDesc(centerAreaId, pageable);
    }

    /**
     * @param centerAreaId 项目计划列表，不分页
     * @return
     */
    public List<TrainProjectPlan> findAll(String centerAreaId) {

        return trainProjectPlanRepository.findAllByCenterAreaId(centerAreaId);
    }


}
