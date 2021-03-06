package com.project.information.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 我收藏和发布的文章
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "my_article",indexes = {@Index(columnList = "pk_Id", name = "pk_Id_index")})
@org.hibernate.annotations.Table(appliesTo = "my_article", comment = "我的文章")
@AllArgsConstructor
@NoArgsConstructor
public class MyArticle extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 40, name = "pk_Id", nullable = false)
	private String pkId;

	@Column(length = 40, name = "article_id", nullable = false)
	private String articleId;//文章id

	@Column(length = 40, name = "user_id", nullable = false)
	private String userId;//用户id

	/**
	 * 我的文章标签类型 0：我发布的 1：我收藏的 2:点赞
	 */
	@Column(name = "tag_type", nullable = false)
	private int tagType;//文章id
}
