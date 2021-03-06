package com.project.information.repository;

import com.project.information.domain.Article;
import com.project.information.dto.IArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

	public Article findByArticleId(String articleId);

	@Transactional(readOnly = true)
	public Page<IArticle> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated,Pageable pageable);

	@Transactional(readOnly = true)
	public Page<IArticle> findAllByArticleTypeAndIsValidatedOrderByCreateTimeDesc(String articleType,String isValidated,Pageable pageable);

	@Modifying
	@Query("update  Article  set isValidated='1' where articleId =?1")
	public int deleteArticleById(String articleId);

//	/**
//	 * 分页查看资讯信息
//	 */
//	public Page<IArticle> findByUserIdOrderByCreateTimeDesc(String userId, Pageable pageable);
//
//
//	public Page<IArticle> findAllByOrderByCreateTimeDesc(Pageable pageable);
//
//
//	@Modifying
//	@Query("update  Article  set isValidated='1' where articleId in(?1)")
//	public int delMoreByArticleIds(List<String> articleIds);
	


//	/**
//	 * 精华
//	 * @param articleIds
//	 * @return
//	 */
//	@Modifying
//	@Query("update  Article  set isNice=?2 where articleId =?1")
//	public void addNice(String articleIds, String value);


//	/**
//	 * 收藏数量
//	 * @return
//	 */
//	@Modifying
//	@Query("update  Article  set collectCount=collectCount+1 where articleId =?1")
//	public int addCollectCount(String articleId);

//	/**
//	 * 点赞数量
//	 * @return
//	 */
//	@Modifying
//	@Query("update  Article  set clickGood=clickGood+1 where articleId =?1")
//	public int addClickGood(String articleId);

//	/**
//	 * 点击数量
//	 * @return
//	 */
//	@Modifying
//	@Query("update  Article  set clickCount=clickCount+1 where articleId =?1")
//	public int addClickCount(String articleId);

}
