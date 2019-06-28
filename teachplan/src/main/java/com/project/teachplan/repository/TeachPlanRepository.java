package com.project.teachplan.repository;

import com.project.teachplan.domain.TeachPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 课时费管理
 */
@Repository("teachPlanRepository")
public interface TeachPlanRepository extends JpaRepository<TeachPlan, String>, JpaSpecificationExecutor<TeachPlan> {

}
