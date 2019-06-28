package com.project.user.domain;


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

/**
 * 用户信息
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "user", comment = "用户信息")
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends Entitys {

    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR(32) COMMENT '用户编号'")
    private String userId;

    @Column(name = "user_name", columnDefinition = "VARCHAR(32) COMMENT '用户名称'")
    private String userName;

    public User(String userId, String userName,  String centerId) {
        this.userId = userId;
        this.userName = userName;
        super.centerAreaId=centerId;
    }
}
