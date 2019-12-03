package com.project.portal.course.request;

import com.project.course.web.vo.ChapterSortVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-12-3 14:26
 * @version: 1.0
 * @description:
 */
@Data
public class ChapterSortReq implements Serializable {
    private List<ChapterSortVo> chapterIdVos;
}
