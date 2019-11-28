package com.project.flow.repository;

import com.project.flow.domain.FlowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 流程信息管理
 */
@Repository("flowInfoRepository")
public interface FlowInfoRepository extends JpaRepository<FlowInfo, String>, JpaSpecificationExecutor<FlowInfo> {


}
