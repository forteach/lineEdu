package com.project.classfee.domain;


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

/**
 * 年度课时费汇总记录
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_fee_year", comment = "年度课时费汇总记录")
@Table(name = "class_fee_year")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassFeeYear extends Entitys {

    @Id
    @Column(name = "class_fee_id", columnDefinition = "VARCHAR(32) COMMENT '课时费编号'")
    private String classFeeId;

    @Column(name = "create_year", columnDefinition = "VARCHAR(32) COMMENT '创建所属的学年'")
    private String createYear;

    @Column(name = "calss_fee_sum", columnDefinition = " COMMENT '课时费总金额'")
    private int calssFeeSum;

    @Column(name = "create_month", columnDefinition = "VARCHAR(32) COMMENT '课时费所属月份'")
    private int create_month;

    @Column(name = "class_sum", columnDefinition = " COMMENT '课时数量'")
    private int classSum;

    @Column(name = "out_month", columnDefinition = " COMMENT '课时费超出月份'")
    private int outMonth;

    @Column(name = "out_fee", columnDefinition = "VARCHAR(32) COMMENT '课时费超出金额")
    private int outFee;

    @Column(name = "out_state", columnDefinition = "VARCHAR(32) COMMENT '课时费超出状态  Y 超出 N 未超出")
    private String outState;

    public ClassFeeYear(String classFeeId, String createYear, int calssFeeSum, int create_month, int classSum, int outMonth, int outFee,String outState, String centerId) {
        this.classFeeId = classFeeId;
        this.createYear = createYear;
        this.calssFeeSum = calssFeeSum;
        this.create_month = create_month;
        this.classSum = classSum;
        this.outMonth = outMonth;
        this.outFee = outFee;
        this.outState = outState;
        super.centerAreaId=centerId;
    }
}
