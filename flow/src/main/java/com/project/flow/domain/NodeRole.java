package com.project.flow.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 流程节点角色管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@IdClass(NodeRolePk.class)
@org.hibernate.annotations.Table(appliesTo = "node_role", comment = "流程节点角色管理")
@Table(name = "node_role", indexes = {
        @Index(columnList = "node_id", name = "node_id_index"),
        @Index(columnList = "role_id", name = "role_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class NodeRole extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private NodeRolePk nodeRolePk;

    private String nodeId;

    private String roleId;

    @Column(name = "role_name", columnDefinition = "VARCHAR(32) COMMENT '节点角色名称'")
    private String nodeName;

//    public NodeRole(String nodeId, String roleId, String nodeName, String centerId) {
//        this.nodeId = nodeId;
//        this.nodeName = nodeName;
//        this.roleId = roleId;
//        super.centerAreaId=centerId;
//    }
}
