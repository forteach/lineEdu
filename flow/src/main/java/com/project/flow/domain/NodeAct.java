package com.project.flow.domain;


import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 流程节点行为管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "node_act", comment = "流程节点行为管理")
@Table(name = "node_act")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class NodeAct extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "act_id", columnDefinition = "VARCHAR(32) COMMENT '流程节点行为编号'")
    private String actId;

    @Column(name = "act_name", columnDefinition = "VARCHAR(32) COMMENT '流程节点行为名称'")
    private String actName;

    @Column(name = "now_node_id", columnDefinition = "VARCHAR(32) COMMENT '当前节点'")
    private String nowNodeId;

    @Column(name = "now_node_name", columnDefinition = "VARCHAR(32) COMMENT '当前节点名称'")
    private String nowNodeName;

    @Column(name = "next_node_id", columnDefinition = "VARCHAR(32) COMMENT '下一节点节点'")
    private String nextNodeId;

    @Column(name = "next_node_name", columnDefinition = "VARCHAR(32) COMMENT '下一节点节点名称'")
    private String nextNodeName;

    @Column(name = "flow_id", columnDefinition = "VARCHAR(32) COMMENT '流程编号'")
    private String flowId;

    public NodeAct(String actId, String actName, String nowNodeId, String nowNodeName, String nextNodeId, String nextNodeName, String flowId,String centerId) {
        this.actId = actId;
        this.actName = actName;
        this.nowNodeId = nowNodeId;
        this.nowNodeName = nowNodeName;
        this.nextNodeId = nextNodeId;
        this.nextNodeName = nextNodeName;
        this.flowId = flowId;
        super.centerAreaId=centerId;
    }
}
