package com.project.portal.information.request.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


/**
 * 创建资讯
 * 
 * @author zjw
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "创建资讯", description = "创建资讯")
public class SaveArticleRequest implements Serializable {
	
	/** 文章类型. */
	public static String STATUS_TEXT = "txt";
	
	/** 链接类型. */
	public static String STATUS_LINK = "link";

	/** 图片类型. */
	public static String STATUS_IMAGE = "img";

	/** 文章编号.**/
	@ApiModelProperty(name = "articleId", value = "文章编号", dataType = "string")
	private String articleId;

	/** 发布人编号.**/
	@ApiModelProperty(name = "发布人编号", value = "发布人编号", dataType = "string")
	private String userId;

	/** 文章题目.**/
	@ApiModelProperty(name = "文章题目", value = "文章题目", dataType = "string")
	private String title;

	// 图片连接.**/
	@ApiModelProperty(name = "imgUrl", value = "图片连接", dataType = "string")
	private String imgUrl;

	// 文章连接.**/
	@ApiModelProperty(name = "linkUrl", value = "文章连接", dataType = "string")
	private String linkUrl;

	/** 文章描述.**/
	@ApiModelProperty(name = "description", value = "文章描述", dataType = "string")
	private String description;

	/** 文章内容.**/
	@ApiModelProperty(name = "articleConten", value = "文章内容", dataType = "string")
	private String articleConten;

	/** 资讯分类. **/
	@ApiModelProperty(name = "articleType", value = "资讯分类", dataType = "string")
	private String articleType;

}