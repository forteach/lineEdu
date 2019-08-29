package com.project.train.repository;


import com.project.train.domain.TrainPlanFinish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 培训计划资料填写情况
 */
@Repository("trainPlanFinishRepository")
public interface TrainPlanFinishRepository extends JpaRepository<TrainPlanFinish, String>, JpaSpecificationExecutor<TrainPlanFinish> {

    public TrainPlanFinish findByPjPlanId(String planId);

    public List<TrainPlanFinish> findTop4ByCenterAreaIdAndIsAllOrderByCreateTime(String centerId,int isAll);


    //判断符合条件是否存在
    public boolean existsByPjPlanIdAndIsCourseAndIsClassAndIsStudentAndIsFile(String planId,int isCourse,int isClass,int isStu,int isFile);
}
