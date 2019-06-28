package com.project.schoolroll.domain;


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
 * 学籍信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "school_roll", comment = "学籍信息")
@Table(name = "school_roll")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRoll extends Entitys implements Serializable {

    @Id
    @Column(name = "roll_id", columnDefinition = "VARCHAR(32) COMMENT '学籍ID'")
    private String rollId;

    @Column(name = "roll_code", columnDefinition = "VARCHAR(32) COMMENT '学籍学校编号'")
    private String rollCode;

    public SchoolRoll(String rollId, String rollCode, String centerId) {
        this.rollId = rollId;
        this.rollCode = rollCode;
        super.centerAreaId=centerId;
    }
}
