package com.project.user.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:　系统栏目表
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/31 9:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "action_column", indexes = {@Index(columnList = "col_id", name = "col_id_index")})
@org.hibernate.annotations.Table(appliesTo = "action_column", comment = "系统栏目表")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ActionColumn extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "col_id", columnDefinition = "varchar(32) COMMENT '栏目编号'")
    private String colId;

    @Column(name = "col_name", columnDefinition = "varchar(40) COMMENT '栏目名称'")
    private String colName;

    @Column(name = "col_name_model", columnDefinition = "varchar(40) COMMENT '栏目跳转'")
    private String colNameModel;

    @Column(name = "col_parent_id", columnDefinition = "varchar(32) COMMENT '父栏目编号'")
    private String colParentId;

    @Column(name = "col_parent_name", columnDefinition = "varchar(40) COMMENT '父栏目名称'")
    private String colParentName;

    @Column(name = "col_url", columnDefinition = "varchar(255) COMMENT '链接地址'")
    private String colUrl;

    @Column(name = "col_img_url", columnDefinition = "varchar(255) COMMENT '链接图标'")
    private String colImgUrl;

    @Column(name = "is_order", columnDefinition = "int(11) COMMENT '栏目顺序'")
    private Integer isOrder;

    @Transient
    private List<ActionColumn> children;
}