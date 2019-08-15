package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.date.DateUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainProjectPlan;
import com.project.train.repository.TrainProjectPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目计划信息记录
 */

@Slf4j
@Service
public class TrainPlanService extends BaseMySqlService {

    @Resource
    private TrainProjectPlanRepository trainProjectPlanRepository;


    /**
     * 项目计划添加
     */
    public TrainProjectPlan save(TrainProjectPlan trainProjectPlan){
        trainProjectPlan.setPjPlanId(IdUtil.fastSimpleUUID());
        return trainProjectPlanRepository.save(trainProjectPlan);
    }

    /**
     * 项目计划添加
     */
    public TrainProjectPlan update(TrainProjectPlan trainProjectPlan){
        TrainProjectPlan obj= findId(trainProjectPlan.getPjPlanId());
        BeanUtil.copyProperties(trainProjectPlan,obj);
        return trainProjectPlanRepository.save(obj);
    }


    /**
     * 项目计划BYID
     * @param planId
     * @return
     */
    public TrainProjectPlan findId(String planId){
        Optional<TrainProjectPlan> obj=trainProjectPlanRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014,"未找到该条记录");
        return obj.get();
    }

    /**
     *
     * @param centerAreaId  获取前多少天项目计划列表
     * @param agoDay    前多少天
     * @param pageable
     * @return
     */
    public Page<TrainProjectPlan> findAllPage(String centerAreaId,int agoDay, Pageable pageable) {
        //提前天数的日期
        String fromDay=DateUtil.formatDate(DateUtil.offsetDay(new Date(),agoDay));
        //当前日期
        String toDay=DateUtil.formatDate(DateUtil.date());
       return trainProjectPlanRepository.findAllByCenterAreaIdAndTrainStratBetweenOrderByTrainStratDesc(centerAreaId,fromDay,toDay,pageable);
    }

    /**
     *
     * @param centerAreaId  获取所有项目计划列表
     * @param pageable
     * @return
     */
    public Page<TrainProjectPlan> findAllPage(String centerAreaId, Pageable pageable) {

        return trainProjectPlanRepository.findAllByCenterAreaIdOrderByTrainStratDesc(centerAreaId,pageable);
    }

    /**
     *
     * @param centerAreaId  项目计划列表，不分页
     * @return
     */
    public List<TrainProjectPlan> findAll(String centerAreaId) {

        return trainProjectPlanRepository.findAllByCenterAreaId(centerAreaId);
    }


}
