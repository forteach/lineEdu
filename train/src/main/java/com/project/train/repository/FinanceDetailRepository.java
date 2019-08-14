package com.project.train.repository;


import com.project.train.domain.FinanceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目财务明细管理
 */
@Repository("financeDetailRepository")
public interface FinanceDetailRepository extends JpaRepository<FinanceDetail, String>, JpaSpecificationExecutor<FinanceDetail> {


}
