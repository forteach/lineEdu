package com.project.classfee.domain;


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
 * 课时费标准
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_fee", comment = "课时费管理")
@Table(name = "class_fee", indexes = {@Index(columnList = "class_fee_id", name = "class_fee_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassFee extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "class_fee_id", columnDefinition = "VARCHAR(32) COMMENT '课时费编号'")
    private String classFeeId;

    @Column(name = "fee_year_id", columnDefinition = "VARCHAR(32) COMMENT '创建所属的学年'")
    private String feeYearId;

    @Column(name = "specialty_ids", columnDefinition = "VARCHAR(32) COMMENT '所属专业'")
    private String specialtyIds;

    @Column(name = "class_fee_sum", columnDefinition = "int(11) COMMENT '课时费总金额'")
    private int classFeeSum;

    @Column(name = "create_month", columnDefinition = "int(11) COMMENT '课时费所属月份'")
    private int createMonth;

    @Column(name = "class_sum", columnDefinition = "int(11) COMMENT '课时数量'")
    private int classSum;

    @Column(name = "balance_state", columnDefinition = "VARCHAR(32) COMMENT '课时费结算状态 no 未结算，part部分结算、all全部结算'")
    private String balanceState;

    @Column(name = "balance_sum", columnDefinition = "int(11) COMMENT '课时费已结算金额'")
    private int balanceSum;

    public ClassFee(String classFeeId, String feeYearId, int classFeeSum, int createMonth, int classSum, String balanceState, int balanceSum, String centerId) {
        this.classFeeId = classFeeId;
        this.feeYearId = feeYearId;
        this.classFeeSum = classFeeSum;
        this.createMonth = createMonth;
        this.classSum = classSum;
        this.balanceState = balanceState;
        this.balanceSum = balanceSum;
        super.centerAreaId=centerId;
    }
}
