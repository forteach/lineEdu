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
 * @date: 19-7-2 18:14
 * @version: 1.0
 * @description:　学习中心
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "learn_center", comment = "学生中心")
@Table(name = "learn_center", indexes = {
        @Index(columnList = "center_id",name = "center_id_index")
})
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
    @Column(name = "center_name", columnDefinition = "VARCHAR(64) COMMENT '学习中心名称'")
    private String centerName;
    /**
     * 地址
     */
    @Column(name = "address", columnDefinition = "VARCHAR(255) COMMENT '学校地址'")
    private String address;
    /**
     * 企业负责人
     */
    @Column(name = "principal", columnDefinition = "VARCHAR(32) COMMENT '企业负责人'")
    private String principal;
    /**
     * 联系电话
     */
    @Column(name = "phone", columnDefinition = "VARCHAR(32) COMMENT '联系电话'")
    private String phone;
    /**
     * 银行名称
     */
    @Column(name = "bank_name", columnDefinition = "VARCHAR(128) COMMENT '银行名称'")
    private String bankName;
    /**
     * 银行账户
     */
    @Column(name = "banking_account", columnDefinition = "VARCHAR(64) COMMENT '银行账户'")
    private String bankingAccount;
    /**
     * 开户人
     */
    @Column(name = "account_holder", columnDefinition = "VARCHAR(32) COMMENT '开户人'")
    private String accountHolder;
    /**
     * 开户人电话
     */
    @Column(name = "account_holder_phone", columnDefinition = "VARCHAR(32) COMMENT '开户人电话'")
    private String accountHolderPhone;
    /**
     * 开户行地址
     */
    @Column(name = "banking_account_address", columnDefinition = "VARCHAR(255) COMMENT '开户行地址'")
    private String bankingAccountAddress;

    @Column(name = "company_address", columnDefinition = "VARCHAR(255) COMMENT '公司地址'")
    private String companyAddress;

    @Column(name = "company_name", columnDefinition = "VARCHAR(128) COMMENT '公司名称'")
    private String companyName;

    @Column(name = "school_admin", columnDefinition = "VARCHAR(64) comment '学校校内联系人'")
    private String schoolAdmin;

    @Column(name = "school_plone", columnDefinition = "VARCHAR(32) COMMENT '学校联系人电话'")
    private String schoolPhone;

    @Column(name = "end_date", columnDefinition = "VARCHAR(32) COMMENT '学习中心结束时间'")
    private String endDate;

    @Column(name = "role_id", columnDefinition = "TINYINT(3) DEFAULT 1 COMMENT '0 总部 1 分中心'", nullable = false)
    private Integer roleId = this.roleId == null ? 1 : this.roleId;
}