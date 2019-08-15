package com.project.train.repository;

import com.project.train.domain.FinanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 培训项目班级资料管理
 */
@Repository("financeTypeRepository")
public interface FinanceTypeRepository extends JpaRepository<FinanceType, String>, JpaSpecificationExecutor<FinanceType> {

    //所有的项目计划列表
    public List<FinanceType> findAllByCenterAreaId(String centerId);
}
