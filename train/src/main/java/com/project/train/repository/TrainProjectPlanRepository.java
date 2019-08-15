package com.project.train.repository;

import com.project.train.domain.TrainProjectPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 培训项目计划管理
 */
@Repository("trainProjectPlanRepository")
public interface TrainProjectPlanRepository extends JpaRepository<TrainProjectPlan, String>, JpaSpecificationExecutor<TrainProjectPlan> {

    //按中心编号和计划执行开始日期查询
    public Page<TrainProjectPlan> findAllByCenterAreaIdAndTrainStartBetweenOrderByTrainStartDesc(String centerId, String form, String to, Pageable pageable);

    //所有的项目计划列表
    public List<TrainProjectPlan>  findAllByCenterAreaId(String centerId);

    //所有的项目计划列表
    public Page<TrainProjectPlan>  findAllByCenterAreaIdOrderByTrainStartDesc(String centerId,Pageable pageable);
}
