package com.project.information.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:　友情链接资讯
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/8 15:03
 */
@Data
@Entity
@Table(name = "friendly_link", indexes = {@Index(columnList = "link_id", name = "link_id_index")})
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "friendly_link", comment = "友情链接资讯")
@AllArgsConstructor
@NoArgsConstructor
public class FriendlyLink extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "link_id", columnDefinition = "varchar(32) COMMENT '连接id'")
    private String linkId;

    @Column(name = "link_name", columnDefinition = "varchar(60) COMMENT '友情链接名称'")
    private String linkName;

    @Column(name = "link_url", columnDefinition = "varchar(255) COMMENT '链接地址'")
    private String linkUrl;

    @Column(name = "link_logo", columnDefinition = "varchar(255) COMMENT 'LOGO图片'")
    private String linkLogo;

    @Column(name = "show_order", columnDefinition = "int(11) COMMENT '页面显示顺序'")
    private Integer showOrder;
}
