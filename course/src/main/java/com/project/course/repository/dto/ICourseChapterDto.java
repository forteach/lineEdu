package com.project.course.repository.dto;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 09:49
 * @Version: 1.0
 * @Description:
 */

public interface ICourseChapterDto {

    /**
     * 章节编号
     *
     * @return
     */
    public String getChapterId();

    /**
     * 章节名称
     *
     * @return
     */
    public String getChapterName();

    /**
     * 章节父ID
     *
     * @return
     */
    public String getChapterParentId();

    /**
     * 是否发布
     *
     * @return
     */
    public String getPublish();

    /**
     * 当前层级位置
     *
     * @return
     */
    public Integer getSort();

    /**
     * 当前章节在树状目录处于层级位置
     *
     * @return
     */
    public Integer getChapterLevel();

    /** 章节随机题目数量*/
    public Integer getRandomQuestionsNumber();
    /** 视频观看长度 videoTime*/
    public Integer getVideoTime();
}