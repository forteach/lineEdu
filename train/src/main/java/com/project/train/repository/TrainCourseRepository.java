package com.project.train.repository;

import com.project.train.domain.TrainCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 培训项目课程管理
 */
@Repository("trainCourseRepository")
public interface TrainCourseRepository extends JpaRepository<TrainCourse, String>, JpaSpecificationExecutor<TrainCourse> {

    boolean existsByCourseName(String courseName);

    /**所有的项目计划列表*/
    List<TrainCourse> findAllByCenterAreaIdOrderByCreateTimeDesc(String centerId);

    Page<TrainCourse> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);
}
