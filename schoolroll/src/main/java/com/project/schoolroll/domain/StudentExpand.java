package com.project.schoolroll.domain;

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
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 16:57
 * @version: 1.0
 * @description:　学生补充信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "student_expand", comment = "学生补充信息")
@Table(name = "student_expand", indexes = {@Index(name = "expand_id_index", columnList = "expand_id")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentExpand extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 补充编号
     */
    @Id
    @Column(name = "expand_id", columnDefinition = "VARCHAR(32) COMMENT '补充编号/扩展编号'")
    private String expandId;
    /**
     * 学生id
     */
    @Column(name = "stu_id", columnDefinition = "VARCHAR(32) COMMENT '学生id'")
    private String stuId;
    /**
     * 补充字段名称
     */
    @Column(name = "expand_name", columnDefinition = "VARCHAR(32) COMMENT '补充字段名称'")
    private String expandName;
    /**
     * 补充字段值
     */
    @Column(name = "expand_value", columnDefinition = "VARCHAR(255) COMMENT '补充字段值'")
    private String expandValue;
}
