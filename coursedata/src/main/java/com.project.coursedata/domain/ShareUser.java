package com.project.coursedata.domain;

import lombok.Data;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-16 15:55
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@org.hibernate.annotations.Table(appliesTo = "share_user", comment = "分享用户")
@Table(name = "share_user", indexes = {@Index(name = "id", columnList = "id")})
public class ShareUser implements Serializable {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(32) COMMENT '分享id'")
    private String shareUserId;


}
