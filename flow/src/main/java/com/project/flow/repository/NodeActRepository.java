package com.project.flow.repository;


import com.project.flow.domain.NodeAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 流程节点行为管理
 */
@Repository("nodeActRepository")
public interface NodeActRepository extends JpaRepository<NodeAct, String>, JpaSpecificationExecutor<NodeAct> {


}
