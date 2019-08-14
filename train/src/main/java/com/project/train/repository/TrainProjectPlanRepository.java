package com.project.train.repository;

import com.project.train.domain.TrainProjectPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目计划管理
 */
@Repository("trainProjectPlanRepository")
public interface TrainProjectPlanRepository extends JpaRepository<TrainProjectPlan, String>, JpaSpecificationExecutor<TrainProjectPlan> {


}
