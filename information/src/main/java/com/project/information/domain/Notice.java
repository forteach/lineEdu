package com.project.information.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 简单公告
 */

@Data
@Entity
@Table(name = "notice")
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "notice", comment = "公告")
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends Entitys implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /** 文章编号.**/
    @Id
    @Column(length = 40, nullable = false, name = "notice_id")
    private String noticeId;

    /** 公告内容.**/
    @Column(length = 2000, nullable = false, name = "content")
    private String content;

    /** 公告内容响应领域 1 全部领域 2 单个领域.**/
    @Column(nullable = false, name = "area")
    private int area;

}
