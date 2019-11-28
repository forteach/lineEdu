package com.project.flow.domain;


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
 * 流程节点管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "node_info", comment = "流程节点管理")
@Table(name = "node_info", indexes = {@Index(columnList = "node_id", name = "node_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class NodeInfo extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "node_id", columnDefinition = "VARCHAR(32) COMMENT '流程节点编号'")
    private String nodeId;

    @Column(name = "node_name", columnDefinition = "VARCHAR(32) COMMENT '流程节点名称'")
    private String nodeName;

    @Column(name = "is_start", columnDefinition = "VARCHAR(32) COMMENT '是否是开始节点'")
    private String isStart;

    @Column(name = "is_end", columnDefinition = "VARCHAR(32) COMMENT '是否是结束节点'")
    private String isEnd;

    @Column(name = "flow_id", columnDefinition = "VARCHAR(32) COMMENT '流程编号'")
    private String flowId;

    public NodeInfo(String nodeId, String nodeName, String isStart, String isEnd, String flowId,String centerId) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.flowId = flowId;
        super.centerAreaId=centerId;
    }
}
