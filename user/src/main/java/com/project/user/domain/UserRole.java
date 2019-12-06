package com.project.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mysql.domain.Entitys;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @Description:　用户角色
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/31 9:19
 */
@Data
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_role", indexes = {
        @Index(columnList = "role_id", name = "role_id_index"),
        @Index(name = "user_id_index", columnList = "user_id")
})
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserRoleFundPrimarykey.class)
@org.hibernate.annotations.Table(appliesTo = "user_role", comment = "用户角色")
public class UserRole extends Entitys {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private UserRoleFundPrimarykey userRoleFundPrimarykey;

    private String userId;

    private String roleId;
}