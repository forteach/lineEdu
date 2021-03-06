package com.project.train.repository;


import com.project.train.domain.FinanceDetailFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 培训项目计划财务凭证
 */
@Repository("financeDetailFileRepository")
public interface FinanceDetailFileRepository extends JpaRepository<FinanceDetailFile, String>, JpaSpecificationExecutor<FinanceDetailFile> {

    /**
     * 项目计划的文件列表
     */
    public Page<FinanceDetailFile> findAllByCenterAreaIdAndPjPlanIdOrderByCreateTimeDesc(String centerId, String pjPlanId, Pageable pageable);

    /**
     * 所有的文件列表
     */
    public Page<FinanceDetailFile> findAllByCenterAreaIdOrderByCreateTimeDesc(String centerId, Pageable pageable);
}
