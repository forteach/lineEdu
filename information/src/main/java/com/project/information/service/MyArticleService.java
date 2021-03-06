package com.project.information.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.information.domain.MyArticle;
import com.project.information.repository.MyArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class MyArticleService {

	//MyArticle TagType 本人发布、收藏、点赞
	@Autowired
	private MyArticleDao myArticleDao;

	/**
	 *
	 * @param id
	 * @param userId
	 * @param articleId
	 * @param tagType  本人发布 点赞  收藏
	 * @return
	 */
	public MyArticle setMyArticle(String id, String userId, String articleId, int tagType) {
		MyArticle myArticle=null;

		if (StrUtil.isBlank(id)) {
			// 创建资讯DOMAIN对象
			myArticle = new MyArticle();
			myArticle.setPkId(IdUtil.fastSimpleUUID());
		}else{
            myArticle = myArticleDao.findByPkId(id);
            MyAssert.isNull(myArticle, DefineCode.ERR0013,"该信息不存在");
		}
		// 获得页面设置的资讯值

		myArticle.setUserId(userId);
		myArticle.setArticleId(articleId);
		myArticle.setTagType(tagType);
		return myArticle;
	}

	public MyArticle save(MyArticle myArticle) {

		return myArticleDao.save(myArticle);
	}

	/**
	 * 判断我的资讯是否存在
	 * @param articleId
	 * @param userId
	 * @param tagType  我的文章标签类型 0：我发布的 1：我收藏的
	 * @return
	 */
   public boolean exixtsMyArticle(String articleId,String userId,int tagType){
		return myArticleDao.existsByArticleIdAndUserIdAndTagType(articleId,userId,tagType);
   }

	public List<MyArticle> findByUserIdtagType(String userId, int tagType) {
		List<MyArticle> list = myArticleDao.findByUserIdAndTagType(userId, tagType);
		return list;
	}

	@Transactional
	public String deleteMyArticleById(String id) {
		 myArticleDao.deleteById(id);
		return "Y";
	}

	@Transactional
	public String deleteMyArticle(String articleId,String userId,int tagType) {
		 myArticleDao.deleteByUserIdAndArticleIdAndTagType(userId,articleId,tagType);
		return "Y";
	}

}
