package com.project.train.repository;


import com.project.train.domain.FinanceDetail;
import com.project.train.domain.TrainProjectPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 培训项目财务明细管理
 */
@Repository("financeDetailRepository")
public interface FinanceDetailRepository extends JpaRepository<FinanceDetail, String>, JpaSpecificationExecutor<FinanceDetail> {

    //按中心编号和计划执行开始日期查询
    public Page<FinanceDetail> findAllByCenterAreaIdAndCreateTimeAfterOrderByCreateTimeDesc(String centerId, String form, Pageable pageable);

    //所有的项目计划列表
    public Page<FinanceDetail>  findAllByCenterAreaIdOrderByCreateTimeDesc(String centerId,Pageable pageable);

    public Page<FinanceDetail> findAllByPjPlanIdAndCenterAreaIdAndCreateTimeAfterOrderByCreateTimeDesc(String pjPlanId,String centerId, String form, Pageable pageable);

}
