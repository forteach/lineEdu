package com.project.flow.repository;

import com.project.flow.domain.NodeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 流程节点角色管理
 */
@Repository("nodeRoleRepository")
public interface NodeRoleRepository extends JpaRepository<NodeRole, String>, JpaSpecificationExecutor<NodeRole> {


}
