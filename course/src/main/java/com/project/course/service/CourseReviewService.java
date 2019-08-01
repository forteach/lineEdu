package com.project.course.service;


import com.project.course.domain.CourseReviewDescribe;
import com.project.course.repository.dto.ICourseReviewDto;
import com.project.course.web.req.CourseReviewReq;
import com.project.course.web.resp.CourseReviewResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-9 10:13
 * @version: 1.0
 * @description:
 */
public interface CourseReviewService {
    /**
     * 保存课程提交的记录
     *
     * @param courseReviewDescribe
     */
//    CourseReviewDescribe save(CourseReviewDescribe courseReviewDescribe);

    /**
     * 禁用课程记录
     *
     * @param reviewId
     */
    void deleteReview(String reviewId);

    /**
     * 查询最近一条评论记录节评分
     *
     * @param courseId
     * @return
     */
//    CourseReviewResp findFirstReview(String courseId);

    /**
     * 分页查询课程评论
     *
     * @param reviewReq
     * @return
     */
//    Page<ICourseReviewDto> findReviewListPage(CourseReviewReq reviewReq, PageRequest request);
}
