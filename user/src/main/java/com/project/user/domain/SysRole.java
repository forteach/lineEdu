package com.project.user.domain;

import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:　系统角色
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/31 8:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role", indexes = {@Index(name = "role_id_index", columnList = "role_id")})
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@org.hibernate.annotations.Table(appliesTo = "sys_role", comment = "系统角色")
public class SysRole extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "role_id", columnDefinition = "VARCHAR(255) COMMENT '角色编号'")
    private String roleId;

    @Column(name = "role_name", columnDefinition = "VARCHAR(40) COMMENT '角色名称'")
    private String roleName;

    @Column(name = "is_validated", columnDefinition = "CHAR(1) COMMENT '生效标识 0生效 1失效'")
    private String isValidated;

    @Column(name = "remark", columnDefinition = "VARCHAR(255) COMMENT '备注 角色说明'")
    private String remark;

    @Column(name = "role_activity", columnDefinition = "VARCHAR(32) COMMENT '角色对应权限'")
    private String roleActivity;
}