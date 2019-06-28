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
import java.io.Serializable;

/**
 * 课时费标准
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_fee", comment = "课时费管理")
@Table(name = "class_fee")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassFee extends Entitys implements Serializable {

    @Id
    @Column(name = "class_fee_id", columnDefinition = "VARCHAR(32) COMMENT '课时费编号'")
    private String classFeeId;

    @Column(name = "create_year", columnDefinition = "VARCHAR(32) COMMENT '创建所属的学年'")
    private String createYear;

    @Column(name = "specialty_ids", columnDefinition = "VARCHAR(32) COMMENT '所属专业'")
    private String specialtyIds;

    @Column(name = "class_fee_sum", columnDefinition = "VARCHAR(32) COMMENT '课时费总金额'")
    private int classFeeSum;

    @Column(name = "create_month", columnDefinition = "VARCHAR(32) COMMENT '课时费所属月份'")
    private int create_month;

    @Column(name = "class_sum", columnDefinition = "int(11) COMMENT '课时数量'")
    private int classSum;

    @Column(name = "balance_state", columnDefinition = "VARCHAR(32) COMMENT '课时费结算状态 no 未结算，part部分结算、all全部结算'")
    private int balanceState;

    @Column(name = "balance_sum", columnDefinition = "VARCHAR(32) COMMENT '课时费已结算金额'")
    private int balanceSum;

    public ClassFee(String classFeeId, String createYear, int classFeeSum, int create_month, int classSum, int balanceState, int balanceSum, String centerId) {
        this.classFeeId = classFeeId;
        this.createYear = createYear;
        this.classFeeSum = classFeeSum;
        this.create_month = create_month;
        this.classSum = classSum;
        this.balanceState = balanceState;
        this.balanceSum = balanceSum;
        super.centerAreaId=centerId;
    }
}
