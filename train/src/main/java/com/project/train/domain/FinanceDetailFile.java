package com.project.train.domain;

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
 * 财务凭证文件
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "finance_detail_file", comment = "财务凭证文件")
@Table(name = "finance_detail_file", indexes = {@Index(columnList = "file_id", name = "file_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FinanceDetailFile extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "file_id", columnDefinition = "VARCHAR(40) COMMENT '财务凭证资料编号'")
    private String fileId;

    @Column(name = "file_name", columnDefinition = "VARCHAR(60) COMMENT '财务凭证资料名称'")
    private String fileName;

    @Column(name = "file_url", columnDefinition = "VARCHAR(255) COMMENT '财务凭证资料URL'")
    private String fileUrl;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(60) COMMENT '财务凭证计划编号'")
    private String pjPlanId;

}
