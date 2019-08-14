package com.project.train.repository;


import com.project.train.domain.TrainPlanClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目计划和班级映射
 */
@Repository("trainPlanClassRepository")
public interface TrainPlanClassRepository extends JpaRepository<TrainPlanClass, String>, JpaSpecificationExecutor<TrainPlanClass> {


}
