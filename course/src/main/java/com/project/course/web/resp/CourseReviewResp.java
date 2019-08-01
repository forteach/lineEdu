package com.project.course.web.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-9 10:01
 * @version: 1.0
 * @description:
 */
@Data
@Builder
public class CourseReviewResp implements Serializable {

    private String courseId;

    private String averageScore;

    private Integer reviewAmount;

    CourseReviewDescResp courseReviewDescribe;

    public CourseReviewResp(String courseId, String averageScore, Integer reviewAmount, CourseReviewDescResp courseReviewDescribe) {
        this.courseId = courseId;
        this.averageScore = averageScore;
        this.reviewAmount = reviewAmount;
        this.courseReviewDescribe = courseReviewDescribe;
    }

    public CourseReviewResp() {
    }
}