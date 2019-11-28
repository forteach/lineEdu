package com.project.wechat.mini.app.domain;

import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/21 18:44
 * @Version: 1.0
 * @Description:
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@DynamicInsert
@Table(name = "wechat_log", indexes = {
        @Index(name = "id_index", columnList = "id")
})
@org.hibernate.annotations.Table(appliesTo = "wechat_log", comment = "微信用户登陆日志记录")
public class WeChatLog extends Entitys implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", columnDefinition = "VARCHAR(32) COMMENT 'id'")
    private String id;

    @Column(name = "ip", columnDefinition = "VARCHAR(32) COMMENT 'ip'")
    private String ip;
}