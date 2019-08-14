package com.project.train.repository;


import com.project.train.domain.TrainPlanClass;
import com.project.train.domain.TrainPlanCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目计划和课程映射
 */
@Repository("trainPlanCourseRepository")
public interface TrainPlanCourseRepository extends JpaRepository<TrainPlanCourse, String>, JpaSpecificationExecutor<TrainPlanCourse> {


}
