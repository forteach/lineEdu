package com.project.train.repository;

import com.project.train.domain.TrainClassStu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目班级学生管理
 */
@Repository("trainClassStuRepository")
public interface TrainClassStuRepository extends JpaRepository<TrainClassStu, String>, JpaSpecificationExecutor<TrainClassStu> {


}
