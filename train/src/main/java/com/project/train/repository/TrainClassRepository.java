package com.project.train.repository;


import com.project.train.domain.TrainClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目班级管理
 */
@Repository("trainClassRepository")
public interface TrainClassRepository extends JpaRepository<TrainClass, String>, JpaSpecificationExecutor<TrainClass> {

    //获得计划下的班级信息
    public Page<TrainClass> findByPjPlanIdOrderByCreateTime(String planId, Pageable pageable);
}
