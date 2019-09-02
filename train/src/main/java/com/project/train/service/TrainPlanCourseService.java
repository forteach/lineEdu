package com.project.train.service;

import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainPlanCourse;
import com.project.train.domain.TrainPlanFinish;
import com.project.train.domain.TrainProjectPlan;
import com.project.train.repository.TrainPlanCourseRepository;
import com.project.train.repository.TrainProjectPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 项目计划的课程关联信息记录
 */

@Slf4j
@Service
public class TrainPlanCourseService extends BaseMySqlService {

    @Resource
    private TrainPlanCourseRepository trainPlanCourseRepository;

    @Resource
    private TrainProjectPlanRepository trainProjectPlanRepository;

    @Resource
    private TrainPlanFinishService trainPlanFinishService;

    /**
     * 项目计划添加
     */
    @Transactional
    public List<TrainPlanCourse> saveAll(List<TrainPlanCourse> list) {

        return trainPlanCourseRepository.saveAll(list);
    }

    /**
     * 项目计划修改
     */
    @Transactional
    public List<TrainPlanCourse> saveOrUpdate(String planId, List<TrainPlanCourse> list) {
        if (list.size() > 0) {
            String course = list.stream().map(item -> item.getCourseName().concat(",")).collect(Collectors.joining());
            TrainProjectPlan plan = trainProjectPlanRepository.getOne(planId);
            plan.setTrainCourse(course.substring(0, course.length() - 1));
            trainProjectPlanRepository.save(plan);
            trainPlanCourseRepository.deleteByPjPlanId(planId);

            TrainPlanFinish tf=trainPlanFinishService.findPjPlanId(planId);
            tf.setIsCourse(1);
            trainPlanFinishService.save(tf);

            //判断是否全部完善信息了
            trainPlanFinishService.updateAll(planId);
            return saveAll(list);
        } else {
            TrainProjectPlan plan = trainProjectPlanRepository.getOne(planId);
            plan.setTrainCourse("");
            trainProjectPlanRepository.save(plan);
            trainPlanCourseRepository.deleteByPjPlanId(planId);

            TrainPlanFinish tf=trainPlanFinishService.findPjPlanId(planId);
            tf.setIsCourse(0);
            trainPlanFinishService.save(tf);

            //判断是否全部完善信息了
            trainPlanFinishService.updateAll(planId);
            return null;
        }

    }

    /**
     * @param pjPlanId 项目计划课程列表，不分页
     * @return
     */
    public List<TrainPlanCourse> findAll(String pjPlanId) {

        return trainPlanCourseRepository.findAllByPjPlanId(pjPlanId);
    }

}
