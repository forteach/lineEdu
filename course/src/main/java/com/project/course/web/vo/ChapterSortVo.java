package com.project.course.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-12-3 14:28
 * @version: 1.0
 * @description:
 */
@Data
public class ChapterSortVo implements Serializable {
    /** 章节id*/
    private String chapterId;
    /** 章节位置排序*/
    private String sort;
}
