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

    //获得中心开始节点信息
//    public NodeInfo findByFlowIdAndIsStartAndCenterAreaId(String flowId,String isStart,String centerId);

    //判断中心流程节点是否是开始节点
//    public boolean existsByFlowIdAndNodeIdAndIsStartAndCenterAreaId(String flowId,String nodeId,String isStart,String centerId);

}
