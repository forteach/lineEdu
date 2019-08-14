package com.project.train.repository;


import com.project.train.domain.TrainCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目课程管理
 */
@Repository("trainCourseRepository")
public interface TrainCourseRepository extends JpaRepository<TrainCourse, String>, JpaSpecificationExecutor<TrainCourse> {


}
