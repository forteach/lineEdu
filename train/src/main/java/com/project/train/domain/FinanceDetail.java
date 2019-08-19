package com.project.train.domain;

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
 * 培训财务明细
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "finance_detail", comment = "培训财务明细")
@Table(name = "finance_detail")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class FinanceDetail extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "detail_id", columnDefinition = "VARCHAR(40) COMMENT '培训财务流水编号'")
    private String detailId;

    @Column(name = "finance_type_id", columnDefinition = "VARCHAR(40) COMMENT '培训财务类型Id'")
    private String financeTypeId;

    @Column(name = "finance_type_name", columnDefinition = "VARCHAR(40) COMMENT '培训财务类型名称'")
    private String financeTypeName;

    @Column(name = "train_class_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目班级编号'")
    private String trainClassId;

    @Column(name = "pj_plan_id", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划编号'")
    private String pjPlanId;

    @Column(name = "pj_plan_name", columnDefinition = "VARCHAR(40) COMMENT '培训项目计划名称'")
    private String pjPlanName;

    @Column(name = "in_out", columnDefinition = "VARCHAR(2) COMMENT '收入支出'")
    private String inOut;

    @Column(name = "money", columnDefinition = "int COMMENT '金额'")
    private String money;

    @Column(name = "batches", columnDefinition = "VARCHAR(40) COMMENT '账目批次'")
    private String batches;

    @Column(name = "create_year", columnDefinition = "VARCHAR(10) COMMENT '培训财务发生年份'")
    private String createYear;

    @Column(name = "create_month", columnDefinition = "VARCHAR(2) COMMENT '培训财务发生月份'")
    private String createMonth;

    @Column(name = "happen_time", columnDefinition = "VARCHAR(2) COMMENT '培训财务发生时间'")
    private String happenTime;

    public FinanceDetail(String detailId, String financeTypeId, String trainClassId, String pjPlanId, String financeTypeName, String inOut, String money, String batches, String createYear, String createMonth,String centerId) {
        this.detailId = detailId;
        this.financeTypeId = financeTypeId;
        this.trainClassId = trainClassId;
        this.pjPlanId = pjPlanId;
        this.financeTypeName = financeTypeName;
        this.inOut = inOut;
        this.money = money;
        this.batches = batches;
        this.createYear = createYear;
        this.createMonth = createMonth;
        super.centerAreaId=centerId;
    }
}
