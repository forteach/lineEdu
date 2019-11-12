package com.project.course.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-6 14:48
 * @version: 1.0
 * @description:
 */
@Data
public class ChapterDataFileVo implements Serializable {
    /**
     * 文件Url
     */
    private String fileUrl;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 章节需要关看视频长度
     */
    private Integer videoTime;
    /**
     * 随机题目数量
     */
    private Integer randomQuestionsNumber;
    /**
     * 章节在本层的顺序
     */
    private String sort;
    /**
     * 是否发布 Y/N
     */
    private String publish;
    /**
     * 视频文件长度
     */
    private Integer fileVideoTime;
    /**
     * 当前是否章、节、小节
     */
    private String chapterType;
}