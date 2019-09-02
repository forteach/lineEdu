package com.project.train.service;

import com.project.mysql.service.BaseMySqlService;
import com.project.train.domain.TrainPlanFinish;
import com.project.train.repository.TrainPlanFinishRepository;
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
public class TrainPlanFinishService extends BaseMySqlService {

    @Resource
    private TrainPlanFinishRepository trainPlanFinishRepository;


    /**
     * 计划信息完善执行情况
     */
    public TrainPlanFinish save(TrainPlanFinish trainPlanFinish) {

       return trainPlanFinishRepository.save(trainPlanFinish);
    }

    /**
     * 计划信息完善执行情况  0未完成，1 完成
     * @return
     */
    public List<TrainPlanFinish> findTOP4(String centerId) {

       return trainPlanFinishRepository.findTop4ByCenterAreaIdAndIsAllOrderByCreateTime(centerId,0) ;
    }

    public boolean  existsAll(String planId){
        return trainPlanFinishRepository.existsByPjPlanIdAndIsCourseAndIsClassAndIsStudentAndIsFile(planId,1,1,1,1);
    }

    public TrainPlanFinish findPjPlanId(String planId){
        return trainPlanFinishRepository.findByPjPlanId(planId);
    }

    @Transactional
    public TrainPlanFinish updateAll( String planId){
           TrainPlanFinish tf=null;
            //判断是否全部完善信息了
            boolean  result= existsAll(planId);
            if(result) {
                 tf = findPjPlanId(planId);
                tf.setIsAll(1);
                save(tf);
            }

        return tf;
    }

}
