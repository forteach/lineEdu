package com.project.classfee.domain;


import cn.hutool.core.util.IdUtil;
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
 * 课时费管理明细
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_fee_info", comment = "课时费管理明细")
@Table(name = "class_fee_info", indexes = {@Index(name = "serial_id_index", columnList = "serial_id")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassFeeInfo extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "serial_id", columnDefinition = "VARCHAR(32) COMMENT '课时费明细流水号'")
    private String serialId= IdUtil.fastSimpleUUID();

    @Column(name = "class_fee_id", columnDefinition = "VARCHAR(32) COMMENT '课时费编号'")
    private String classFeeId;

    @Column(name = "full_name", columnDefinition = "VARCHAR(32) COMMENT '姓名'")
    private String fullName;

    @Column(name = "create_year", columnDefinition = "VARCHAR(32) COMMENT '创建所属的学年'")
    private String createYear;

    @Column(name = "create_month", columnDefinition = "VARCHAR(32) COMMENT '创建所属的月份'")
    private String createMonth;

    @Column(name = "specialty_ids", columnDefinition = "VARCHAR(32) COMMENT '所属专业'")
    private String specialtyIds;

    @Column(name = "class_fee", columnDefinition = "VARCHAR(32) COMMENT '每节课的课时费'")
    private int classFee;

    @Column(name = "class_count", columnDefinition = "VARCHAR(32) COMMENT '课时'")
    private int classCount;

    public ClassFeeInfo(String serialId, String classFeeId, String fullName, String createYear, String createMonth, String specialtyIds, int classFee, int classCount,String centerId, String createUser) {
        super(createUser, createUser, centerId);
        this.serialId = serialId;
        this.classFeeId = classFeeId;
        this.fullName = fullName;
        this.createYear = createYear;
        this.createMonth = createMonth;
        this.specialtyIds = specialtyIds;
        this.classFee = classFee;
        this.classCount = classCount;
    }
}
