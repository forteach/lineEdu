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


    /**
     * 项目计划班级添加
     */
    public TrainClass save(TrainClass trainClass){
        trainClass.setTrainClassId(IdUtil.fastSimpleUUID());
        return trainClassRepository.save(trainClass);
    }

    /**
     * 项目计划班级修改
     */
    public TrainClass update(TrainClass trainClass){
        TrainClass obj= findId(trainClass.getTrainClassId());
        BeanUtil.copyProperties(trainClass,obj);
        return trainClassRepository.save(obj);
    }


    /**
     * 项目计划班级BYID
     * @param planId
     * @return
     */
    public TrainClass findId(String planId){
        Optional<TrainClass> obj=trainClassRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014,"未找到该条记录");
        return obj.get();
    }


    /**
     *
     * @param planId  获取计划项目的班级列表
     * @param pageable
     * @return
     */
    public Page<TrainClass> findPlanPage(String planId, Pageable pageable) {

        return trainClassRepository.findByPjPlanIdOrderByCreateTime(planId,pageable);
    }


}
