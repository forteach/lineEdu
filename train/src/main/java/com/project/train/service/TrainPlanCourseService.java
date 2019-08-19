package com.project.train.service;

import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainPlanCourse;
import com.project.train.repository.TrainPlanCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 项目计划的课程关联信息记录
 */

@Slf4j
@Service
public class TrainPlanCourseService extends BaseMySqlService {

    @Resource
    private TrainPlanCourseRepository trainPlanCourseRepository;


    /**
     * 项目计划添加
     */
    @Transactional
    public List<TrainPlanCourse> saveAll(List<TrainPlanCourse> list){

        return trainPlanCourseRepository.saveAll(list);
    }

    /**
     * 项目计划修改
     */
    @Transactional
    public List<TrainPlanCourse> saveOrUpdate(String planId, List<TrainPlanCourse> list){
        trainPlanCourseRepository.deleteByPjPlanId(planId);
        return saveAll(list);
    }

    /**
     *
     * @param planId  项目计划课程列表，不分页
     * @return
     */
    public List<TrainPlanCourse> findAll(String planId) {

        return trainPlanCourseRepository.findAllByPjPlanId(planId);
    }

}
