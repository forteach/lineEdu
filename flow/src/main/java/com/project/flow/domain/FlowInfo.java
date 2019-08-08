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
 * 流程管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "flow_info", comment = "流程管理")
@Table(name = "flow_info")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FlowInfo extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "flow_id", columnDefinition = "VARCHAR(32) COMMENT '流程编号编号'")
    private String flowId;

    @Column(name = "flow_name", columnDefinition = "VARCHAR(32) COMMENT '流程编号名称'")
    private String flowName;

    public FlowInfo(String flowId, String flowName, String centerId) {
        this.flowId = flowId;
        this.flowName = flowName;
        super.centerAreaId=centerId;
    }
}
