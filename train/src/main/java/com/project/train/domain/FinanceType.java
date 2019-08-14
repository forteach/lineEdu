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
 * 培训财务类型字典
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "finance_type", comment = "培训财务类型字典")
@Table(name = "finance_type")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FinanceType extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "finance_type_id", columnDefinition = "VARCHAR(40) COMMENT '培训财务类型编号'")
    private String financeTypeId;

    @Column(name = "finance_type_name", columnDefinition = "VARCHAR(40) COMMENT '培训财务类型名称'")
    private String financeTypeName;

    public FinanceType(String financeTypeId, String financeTypeName,String centerId) {
        this.financeTypeId = financeTypeId;
        this.financeTypeName = financeTypeName;
        super.centerAreaId=centerId;
    }
}
