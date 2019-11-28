package com.project.databank.domain;

import com.project.mysql.domain.Entitys;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-8 15:32
 * @Version: 1.0
 * @Description: 用户关注的文章
 */
@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_attention", indexes = {@Index(columnList = "att_id", name = "att_id_index"), @Index(columnList = "article_id", name = "article_id_index")})
@org.hibernate.annotations.Table(appliesTo = "user_attention", comment = "用户关注的文章")
public class UserAttention extends Entitys implements Serializable {

    @Id
    @Column(name = "att_id", columnDefinition = "varchar(32) COMMENT '关注ID'")
    private String attId;

    @Column(name = "article_id", columnDefinition = "varchar(32) COMMENT '文章编号'")
    private String articleId;

    public UserAttention(String attId, String articleId) {
        this.attId = attId;
        this.articleId = articleId;
    }

    public UserAttention() {
    }
}