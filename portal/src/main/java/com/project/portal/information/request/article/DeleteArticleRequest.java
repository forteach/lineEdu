package com.project.portal.information.request.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteArticleRequest implements Serializable {

	private String articleId;

}
