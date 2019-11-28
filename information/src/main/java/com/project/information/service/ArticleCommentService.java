package com.project.information.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.information.domain.ArticleComment;
import com.project.information.repository.ArticleCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ArticleCommentService {

    //资讯信息在首页只显示6条数据
    @Value("${com.pageSize:6}")
    private String articleHomePageSize;

    /**
     * 学生信息前缀
     */
    public static final String STUDENT_ADO = "studentsData$";

    @Autowired
    private ArticleCommentDao articleCommentDao;


    @Resource
    private HashOperations<String, String, String> hashOperations;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置资料对象数据
     *
     * @param  artcomment
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ArticleComment save(  ArticleComment artcomment) {
        // 获得页面设置的资讯值
        String commentId = artcomment.getCommentId();
        String userId=artcomment.getUserId();
//        ArticleComment artcomment = null;

        // 是否获取已存在的用户信息
        if (StrUtil.isNotBlank(commentId)) {
            artcomment = findById(commentId);
            String createTime=artcomment.getCreateTime();
//            UpdateUtil.copyProperties(request, artcomment);
            artcomment.setCreateTime(createTime);

        }else {
            artcomment = new ArticleComment();
           // UpdateUtil.copyNullProperties(request, artcomment);
            artcomment.setCommentId(IdUtil.fastSimpleUUID());
//            artcomment.setCreateUser(request.getUserId());

            //初始化点击量数据
            String artcommentkey=ArticleKey.ARTCOMMENTGOOD.concat(artcomment.getCommentId());
            stringRedisTemplate.opsForValue().set(artcommentkey,"0");

            //增加资讯回复数量
            String replykey=ArticleKey.ARTCOMMENTREPLY.concat(artcomment.getArticleId());
            String count=stringRedisTemplate.opsForValue().get(replykey);
            int newCount=Integer.valueOf(count).intValue()+1;
            stringRedisTemplate.opsForValue().set(replykey,String.valueOf(newCount));

            if("C".equals(artcomment.getUserType())){
                //学生名称
                artcomment.setUserName(findStudentsName(userId));
                //学生头像
                artcomment.setUserTortrait(findStudentsPortrait(userId));
            }else{
                artcomment.setUserName("教师");
                //TODO 添加教师头像路径？
            }

        }

        return articleCommentDao.save(artcomment);
    }


    /**
     * 根据用户id 从redis 取出名字信息
     *
     * @param id
     * @return
     */
    private String findStudentsName(final String id) {

        String key=STUDENT_ADO.concat(id);
        return hashOperations.get(key, "name");
    }

    /**
     * 从redis 取出头像信息
     *
     * @param id
     * @return
     */
    private String findStudentsPortrait(final String id) {
        return hashOperations.get(STUDENT_ADO.concat(id), "portrait");
    }


    /**
     * 根据ID获取资料信息
     *
     * @param id
     * @return
     */
    public ArticleComment findById(String id) {
        ArticleComment obj=articleCommentDao.findByCommentId(id);
        MyAssert.isNull(obj, DefineCode.ERR0013,"该信息不存在");
        return obj;
    }

    /**
     * 评论点赞
     *
     * @param commentId  评论编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addClickGood(String commentId) {

        //资讯点赞次数+1
        articleCommentDao.addGoodCount(commentId);

        String key=ArticleKey.ARTCOMMENTGOOD.concat(commentId);

        String count=stringRedisTemplate.opsForValue().get(key);
        int newcount=Integer.valueOf(count).intValue()+1;
        stringRedisTemplate.opsForValue().set(key,String.valueOf(newcount));
        return newcount;
    }

    /**
     * 评论回复
     * @param reply  回复内容
     * @param commentId  评论ID
     * @param replyUserName  回复人名称
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveReply( String reply,String commentId,String replyUserName){
        articleCommentDao.saveReply(reply,commentId,DateUtil.now(),replyUserName);
        return "Y";
    }

    /**
     * 根据资讯D，获得资讯评论
     *
     * @param articleId
     * @param pageable
     * @return
     */
    public List<ArticleComment> findByArticleId(String articleId, Pageable pageable) {
        return articleCommentDao.findByArticleIdOrderByCreateTimeDesc(articleId, pageable).getContent();
    }
}
