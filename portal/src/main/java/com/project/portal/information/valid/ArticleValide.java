package com.project.portal.information.valid;


import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.portal.information.request.article.SaveArticleRequest;

public class ArticleValide {

	public static void saveValide(SaveArticleRequest request){
		String articleType=request.getArticleType();
		String title=request.getTitle();
		String userId=request.getUserId();
		
		MyAssert.blank(articleType, DefineCode.ERR0010,"资讯数据类型不能为空");
		MyAssert.blank(title, DefineCode.ERR0010,"资讯标题不能为空");
		MyAssert.blank(userId, DefineCode.ERR0010,"资讯发布人信息不能为空");
	}


}
