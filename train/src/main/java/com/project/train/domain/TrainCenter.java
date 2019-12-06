package com.project.train.domain;

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
 * 培训中心管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "train_center", comment = "培训中心管理")
@Table(name = "train_center", indexes = {@Index(columnList = "train_center_id", name = "train_center_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrainCenter extends Entitys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "train_center_id", columnDefinition = "VARCHAR(40) COMMENT '培训中心编号'")
    private String trainCenterId;

    @Column(name = "train_center_name", columnDefinition = "VARCHAR(40) COMMENT '培训中心编号'")
    private String trainCenterName;

    @Column(name = "train_center_admin", columnDefinition = "VARCHAR(40) COMMENT '培训中心管理员'")
    private String trainCenterAdmin;

    @Column(name = "train_center_pwd", columnDefinition = "VARCHAR(40) COMMENT '培训中心管理员密码'")
    private String trainCenterPwd;

    @Column(name = "is_all_admin", columnDefinition = "VARCHAR(60) COMMENT '是否有总部培训中心管理权力'")
    private String isAllAdmin;
}