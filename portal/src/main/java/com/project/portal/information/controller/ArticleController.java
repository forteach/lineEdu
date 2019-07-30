package com.project.portal.information.controller;

import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.UpdateUtil;
import com.project.information.domain.Article;
import com.project.information.service.ArticleService;
import com.project.portal.information.request.article.ByIdRequest;
import com.project.portal.information.request.article.FindAllRequest;
import com.project.portal.information.request.article.SaveArticleRequest;
import com.project.portal.information.response.article.ArticleListResponse;
import com.project.portal.information.response.article.ArticleResponse;
import com.project.portal.information.valid.ArticleValide;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/article", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "文章资讯资料", tags = {"文章资讯资料操作信息"})
public class ArticleController  {

	@Autowired
	private ArticleService articleService;

	/**
	 * 保存资讯、资讯所属模块信息
	 */

	@PostMapping("/saveOrUpdate")
	public WebResult save(@RequestBody SaveArticleRequest request) {

		// 验证资讯信息
		ArticleValide.saveValide(request);

		// 设置资讯数据
		Article  article =new Article();
		UpdateUtil.copyProperties(request, article);
		Article newArticle = articleService.setDoMain(article);
		articleService.save(newArticle);
		ArticleResponse res=new ArticleResponse();
		UpdateUtil.copyProperties(newArticle, res);

		// 调用save方法
		return WebResult.okResult(res);

	}

	/**
	 * 获得资讯详情
	 * @param req
	 * @return
	 */
	@PostMapping("/findId")
	public WebResult findById(@RequestBody ByIdRequest req) {
		MyAssert.isNull(req.getId(), DefineCode.ERR0010,"编号不能为空");
		Article article = articleService.findById(req.getId());
		ArticleResponse res=new ArticleResponse();
		UpdateUtil.copyProperties(article, res);

		return WebResult.okResult(res);
	}

	/**
	 *逻辑删除资讯内容
	 * @param req
	 * @return
	 */
	@PostMapping("/delId")
	public WebResult deleteArticleById(@RequestBody ByIdRequest req) {
		MyAssert.isNull(req.getId(), DefineCode.ERR0010,"编号不能为空");
		int result = articleService.deleteArticleById(req.getId());
		MyAssert.eq(result, 0, DefineCode.ERR0013, "删除资讯失败");
		return WebResult.okResult(result);
	}

	/**
	 * 所有资讯倒序分页获取
	 * @param req
	 * @return
	 */
	@PostMapping("/findAllDesc")
	public WebResult findAllDesc(@RequestBody FindAllRequest req){
		SortVo sortVo = req.getSortVo();
		PageRequest page = PageRequest.of(sortVo.getPage(), sortVo.getSize());

			return WebResult.okResult(articleService.findAllDesc(req.getArticleType(),"1",page)
					.stream()
					.map(item -> {
						ArticleListResponse ar = new ArticleListResponse();
						UpdateUtil.copyProperties(item, ar);
						return ar;
					})
					.collect(toList()));

	}

}
