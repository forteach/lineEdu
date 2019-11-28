package com.project.course.web.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-24 18:16
 * @version: 1.0
 * @description:
 */
@Data
public class CourseChapterReviewResp implements Serializable {

    private String chapterId;

    private String averageScore;

    private Integer reviewAmount;

    public CourseChapterReviewResp() {
    }

    public CourseChapterReviewResp(String chapterId, String averageScore, Integer reviewAmount) {
        this.chapterId = chapterId;
        this.averageScore = averageScore;
        this.reviewAmount = reviewAmount;
    }
}