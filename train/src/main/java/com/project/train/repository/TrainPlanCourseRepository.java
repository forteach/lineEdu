package com.project.train.repository;


import com.project.train.domain.TrainPlanCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 培训项目计划和课程映射
 */
@Repository("trainPlanCourseRepository")
public interface TrainPlanCourseRepository extends JpaRepository<TrainPlanCourse, String>, JpaSpecificationExecutor<TrainPlanCourse> {

    public List<TrainPlanCourse> findAllByPjPlanId(String pjPlanId);

    public int deleteByPjPlanId(String pjPlanId);
}
