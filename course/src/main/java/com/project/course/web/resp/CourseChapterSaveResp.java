package com.project.course.web.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-28 11:49
 * @Version: 1.0
 * @Description: 返回前端目录树结构
 */
@Data
public class CourseChapterSaveResp implements Serializable {

    private String chapterId;

    private String chapterName;

    private String courseId;

    private String chapterParentId;

    private String sort;

    private String chapterType;

    private String chapterLevel;

    private String publish;

    private Integer randomQuestionsNumber;

    private Integer videoTime;
}
