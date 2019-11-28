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
 * 流程连线信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@IdClass(NodeRolePk.class)
@org.hibernate.annotations.Table(appliesTo = "link_line", comment = "流程连线信息")
@Table(name = "link_line", indexes = {
        @Index(columnList = "node_id", name = "node_id_index"),
        @Index(columnList = "role_id", name = "role_id_index")
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class LinkLine extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private NodeRolePk nodeRolePk;

    private String nodeId;

    private String actId;

    @Column(name = "node_name", columnDefinition = "VARCHAR(32) COMMENT '节点角色名称'")
    private String nodeName;

    @Column(name = "act_name", columnDefinition = "VARCHAR(32) COMMENT '节点角色名称'")
    private String actName;

    public LinkLine(NodeRolePk nodeRolePk, String nodeId, String actId, String nodeName, String actName,String centerId) {
        this.nodeRolePk = nodeRolePk;
        this.nodeId = nodeId;
        this.actId = actId;
        this.nodeName = nodeName;
        this.actName = actName;
        super.centerAreaId=centerId;
    }
}
