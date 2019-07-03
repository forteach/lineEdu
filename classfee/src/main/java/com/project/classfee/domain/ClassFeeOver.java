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
 * 课时费管理结算明细
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_fee_info", comment = "课时费管理结算明细")
@Table(name = "class_fee_info")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassFeeOver extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "over_id", columnDefinition = "VARCHAR(32) COMMENT '课时费结算明细流水号'")
    private String overId;


    @Column(name = "class_fee_id", columnDefinition = "VARCHAR(32) COMMENT '课时费编号'")
    private String classFeeId;

}
