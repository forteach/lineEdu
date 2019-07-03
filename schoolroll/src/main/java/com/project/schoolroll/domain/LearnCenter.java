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
 * @date: 19-7-2 18:14
 * @version: 1.0
 * @description:　学习中心
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "learn_center", comment = "学生中心")
@Table(name = "learn_center")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class LearnCenter extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学习中心编号
     */
    @Id
    @Column(name = "center_id", columnDefinition = "VARCHAR(32) COMMENT '学习中心编号'")
    private String centerId;
    /**
     * 学习中心名称
     */
    @Column(name = "centerName", columnDefinition = "VARCHAR(64) COMMENT '学习中心名称'")
    private String centerName;

}
