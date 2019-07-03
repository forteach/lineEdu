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
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 16:50
 * @version: 1.0
 * @description: 专业信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "specialty", comment = "专业信息")
@Table(name = "specialty")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Specialty extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 专业id
     */
    @Id
    @Column(name = "specialty_id", columnDefinition = "VARCHAR(32) COMMENT '专业id'")
    private String specialtyId;

    /**
     * 专业名称
     */
    @Column(name = "specialty_name", columnDefinition = "VARCHAR(255) COMMENT '专业名称'")
    private String specialtyName;
}