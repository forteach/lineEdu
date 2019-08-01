package com.project.course.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-23 11:21
 * @version: 1.0
 * @description:
 */
@Data
public class CourseChapterReviewSaveReq implements Serializable {

    private String chapterId;

    private Integer score;

    private String studentId;
}