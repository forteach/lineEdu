package com.project.train.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainClassStu;
import com.project.train.repository.TrainClassStuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 项目计划班级成员信息记录
 */

@Slf4j
@Service
public class TrainClassStuService extends BaseMySqlService {

    @Resource
    private TrainClassStuRepository trainClassStuRepository;


    /**
     * 项目计划班级成员添加
     */
    public TrainClassStu save(TrainClassStu trainClassStu){
        trainClassStu.setTrainStuId(IdUtil.fastSimpleUUID());
        return trainClassStuRepository.save(trainClassStu);
    }

    /**
     * 项目计划班级成员修改
     */
    public TrainClassStu update(TrainClassStu trainClassStu){
        TrainClassStu obj= findId(trainClassStu.getTrainStuId());
        BeanUtil.copyProperties(trainClassStu,obj);
        return trainClassStuRepository.save(obj);
    }


    /**
     * 项目计划班级成员BYID
     * @param planId
     * @return
     */
    public TrainClassStu findId(String planId){
        Optional<TrainClassStu> obj=trainClassStuRepository.findById(planId);
        MyAssert.isFalse(obj.isPresent(), DefineCode.ERR0014,"未找到该条记录");
        return obj.get();
    }


    /**
     *
     * @param planId  获取计划项目的班级成员列表
     * @param pageable
     * @return
     */
    public Page<TrainClassStu> findPlanPage(String planId, Pageable pageable) {

        return trainClassStuRepository.findByPjPlanIdOrderByCreateTimeDesc(planId,pageable);
    }


    /**
     *
     * @param classId
     * @param pageable
     * @return
     */
    public Page<TrainClassStu> findClassPage(String classId, Pageable pageable) {

        return trainClassStuRepository.findByTrainClassId(classId,pageable);
    }

}
