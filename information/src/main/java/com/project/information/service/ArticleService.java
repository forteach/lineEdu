package com.project.information.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.RegexUtils;
import com.project.information.domain.Article;
import com.project.information.dto.IArticle;
import com.project.information.repository.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

@Service
public class ArticleService {

    //资讯信息在首页只显示6条数据
    @Value("${com.pageSize:6}")
    private String articleHomePageSize;

    @Autowired
    private ArticleDao articleDao;


    @Transactional(rollbackFor = Exception.class)
    public Article save(Article article) {
        // 保存资讯信息
        Article art = articleDao.save(article);
        // 返回对象为空，保存失败
        MyAssert.isNull(art, DefineCode.ERR0013,"保存资料内容失败");
        return art;
    }


    /**
     * 设置资料对象数据
     *
     * @param newArt
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Article setDoMain(Article newArt) {
        // 获得页面设置的资讯值
        String artId = newArt.getArticleId();
        // 是否获取已存在的用户信息
        if (StrUtil.isNotBlank(artId)) {
            Article art = findById(artId);
            MyAssert.isNull(art, DefineCode.ERR0010,"资料信息不存在");
            if (StrUtil.isNotBlank(newArt.getArticleConten())) {
                String newContent = replaceImgWidth(newArt.getArticleConten());
                newArt.setArticleConten(newContent);
            }
            BeanUtil.copyProperties(newArt, art);
            return art;
        }else {
            if (StrUtil.isNotBlank(newArt.getArticleConten())) {
                String newContent = replaceImgWidth(newArt.getArticleConten());
                newArt.setArticleConten(newContent);
            }
            newArt.setArticleId(IdUtil.fastSimpleUUID());
            newArt.setCreateUser(newArt.getUserId());
            newArt.setIsNice("false");
            return newArt;
        }
    }

    /**
     * 根据ID获取资料信息
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        Article obj=articleDao.findByArticleId(id);
        MyAssert.isNull(obj,DefineCode.ERR0013,"该信息不存在");
        return obj;
    }


    /**
     * 获得倒序的信息列表
     *
     * @param pageable
     * @return
     */
    public Page<IArticle> findAllDesc(String articleType, Pageable pageable) {
        if (StrUtil.isNotBlank(articleType)) {
            return articleDao.findAllByArticleTypeAndIsValidatedOrderByCreateTimeDesc(articleType, TAKE_EFFECT_OPEN, pageable);
        }else {
            return articleDao.findAllByIsValidatedEqualsOrderByCreateTimeDesc(TAKE_EFFECT_OPEN, pageable);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public int deleteArticleById(String articleId) {
        return articleDao.deleteArticleById(articleId);
    }


    //处理内容图片显示宽高
    private String replaceImgWidth(String content) {
        // 宽、高过滤的正则表达式
        String reg1 = "<img (.*?)height=\"(.*?)\\\"";
        String reg2 = "<img (.*?)width=\"(.*?)\\\"";

        content = RegexUtils.replaceAll(content, reg1, "<img $1height=\"100%\\\"").toString();
        content = RegexUtils.replaceAll(content, reg2, "<img $1width=\"100%\\\"").toString();

        return content;
    }
}