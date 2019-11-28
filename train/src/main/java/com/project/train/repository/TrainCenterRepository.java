package com.project.train.repository;

import com.project.train.domain.TrainCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训中心管理
 */
@Repository("trainCenterRepository")
public interface TrainCenterRepository extends JpaRepository<TrainCenter, String>, JpaSpecificationExecutor<TrainCenter> {


}
