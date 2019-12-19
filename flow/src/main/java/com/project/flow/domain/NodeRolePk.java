package com.project.flow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Description: 协作人员访问
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/7 10:18
 */


@Data
@Embeddable
public class NodeRolePk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "node_id", columnDefinition = "VARCHAR(32) COMMENT '节点编号'", insertable = false, updatable = false)
    private String nodeId;

    @Column(name = "role_id", columnDefinition = "VARCHAR(32) COMMENT '角色编号'", insertable = false, updatable = false)
    private String roleId;

    public NodeRolePk() {

    }

//    public NodeRolePk(String nodeId, String roleId) {
//        this.nodeId = nodeId;
//        this.roleId = roleId;
//    }
}
