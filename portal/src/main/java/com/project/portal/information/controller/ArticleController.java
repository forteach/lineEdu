package com.project.portal.information.controller;

import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.information.domain.Article;
import com.project.information.service.ArticleService;
import com.project.portal.information.request.article.ByIdRequest;
import com.project.portal.information.request.article.FindAllRequest;
import com.project.portal.information.request.article.SaveArticleRequest;
import com.project.portal.information.response.article.ArticleResponse;
import com.project.portal.information.valid.ArticleValide;
import com.project.portal.response.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/article", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "文章资讯资料", tags = {"文章资讯资料操作信息"})
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 保存资讯、资讯所属模块信息
     */
    @ApiOperation(value = "保存资讯、资讯所属模块信息")
    @PostMapping("/saveOrUpdate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "发布人编号", dataType = "string", required = true, paramType = "form"),
            @ApiImplicitParam(name = "title", value = "文章题目", dataType = "string", required = true,  paramType = "form"),
            @ApiImplicitParam(name = "imgUrl", value = "图片连接", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "linkUrl", value = "文章连接", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "description", value = "文章描述", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "articleConten", value = "文章内容", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "articleType", value = "资讯分类", dataType = "string", required = true, paramType = "form"),
    })
    public WebResult save(@RequestBody SaveArticleRequest request) {

        // 验证资讯信息
        ArticleValide.saveValide(request);

        // 设置资讯数据
        Article article = new Article();
        BeanUtil.copyProperties(request, article);
        Article newArticle = articleService.setDoMain(article);
        articleService.save(newArticle);
        ArticleResponse res = new ArticleResponse();
        BeanUtil.copyProperties(newArticle, res);

        // 调用save方法
        return WebResult.okResult(res);

    }

    /**
     * 获得资讯详情
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获得资讯详情")
    @PostMapping("/findId")
    @ApiImplicitParam(name = "id", value = "主键编号", dataType = "string", paramType = "form")
    public WebResult findById(@RequestBody ByIdRequest req) {
        MyAssert.isNull(req.getId(), DefineCode.ERR0010, "编号不能为空");
        Article article = articleService.findById(req.getId());
        ArticleResponse res = new ArticleResponse();
        BeanUtil.copyProperties(article, res);

        return WebResult.okResult(res);
    }

    /**
     * 逻辑删除资讯内容
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "逻辑删除资讯内容")
    @ApiImplicitParam(name = "id", value = "主键编号", dataType = "string", paramType = "form")
    @PostMapping("/delId")
    public WebResult deleteArticleById(@RequestBody ByIdRequest req) {
        MyAssert.isNull(req.getId(), DefineCode.ERR0010, "编号不能为空");
        int result = articleService.deleteArticleById(req.getId());
        MyAssert.eq(result, 0, DefineCode.ERR0013, "删除资讯失败");
        return WebResult.okResult(result);
    }

    /**
     * 所有资讯倒序分页获取
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "所有资讯倒序分页获取")
    @PostMapping("/findAllDesc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleType", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
    })
    public WebResult findAllDesc(@RequestBody FindAllRequest request) {
        MyAssert.blank(String.valueOf(request.getPage()), DefineCode.ERR0010, "页码参数不为空");
        MyAssert.blank(String.valueOf(request.getSize()), DefineCode.ERR0010, "每页条数不为空");
        MyAssert.gt(request.getPage(), 0, DefineCode.ERR0010, "页码参数不正确");
        MyAssert.gt(request.getSize(), 0, DefineCode.ERR0010, "每页显示条数不正确");
        PageRequest page = PageRequest.of(request.getPage(), request.getSize());
        return WebResult.okResult(articleService.findAllDesc(request.getArticleType(), page));
    }

}
