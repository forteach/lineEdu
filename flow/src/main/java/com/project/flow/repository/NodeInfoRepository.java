package com.project.flow.repository;

import com.project.flow.domain.NodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 流程管理
 */
@Repository("nodeInfoRepository")
public interface NodeInfoRepository extends JpaRepository<NodeInfo, String>, JpaSpecificationExecutor<NodeInfo> {


}
