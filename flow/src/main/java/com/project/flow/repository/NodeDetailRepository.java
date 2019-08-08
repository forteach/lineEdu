package com.project.flow.repository;

import com.project.flow.domain.NodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 流程执行明细管理
 */
@Repository("nodeDetailRepository")
public interface NodeDetailRepository extends JpaRepository<NodeDetail, String>, JpaSpecificationExecutor<NodeDetail> {


}
