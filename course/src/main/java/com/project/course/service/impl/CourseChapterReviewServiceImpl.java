package com.project.course.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.course.domain.CourseChapterReview;
import com.project.course.domain.CourseChapterReviewDescribe;
import com.project.course.domain.builder.CourseChapterReviewBuilder;
import com.project.course.domain.builder.CourseChapterReviewDescribeBuilder;
import com.project.course.repository.CourseChapterReviewDescribeRepository;
import com.project.course.repository.CourseChapterReviewRepository;
import com.project.course.repository.dto.ICourseChapterReviewCountDto;
import com.project.course.service.CourseChapterReviewService;
import com.project.course.web.req.CourseChapterReviewSaveReq;
import com.project.course.web.resp.CourseChapterReviewResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-23 11:12
 * @version: 1.0
 * @description: 课程章节评价
 */
@Service
@Slf4j
public class CourseChapterReviewServiceImpl implements CourseChapterReviewService {

    private final CourseChapterReviewRepository courseChapterReviewRepository;

    private final CourseChapterReviewDescribeRepository courseChapterReviewDescribeRepository;

    @Autowired
    public CourseChapterReviewServiceImpl(CourseChapterReviewRepository courseChapterReviewRepository, CourseChapterReviewDescribeRepository courseChapterReviewDescribeRepository) {
        this.courseChapterReviewRepository = courseChapterReviewRepository;
        this.courseChapterReviewDescribeRepository = courseChapterReviewDescribeRepository;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(CourseChapterReviewSaveReq reviewSaveReq) {
        Long count = courseChapterReviewDescribeRepository.countByIsValidatedEqualsAndChapterIdAndStudentId(TAKE_EFFECT_OPEN, reviewSaveReq.getChapterId(), reviewSaveReq.getStudentId());
        if (count != null && count > 0) {
            MyAssert.isNull(null, DefineCode.ERR0010, "您已经评价过本章节");
        }

        CourseChapterReviewDescribe courseChapterReviewDescribe = courseChapterReviewDescribeRepository
                .save(CourseChapterReviewDescribeBuilder
                        .aCourseChapterReviewDescribe()
                        .withChapterId(reviewSaveReq.getChapterId())
                        .withScore(reviewSaveReq.getScore())
                        .withStudentId(reviewSaveReq.getStudentId())
                        .withCreateUser(reviewSaveReq.getStudentId())
                        .withUpdateUser(reviewSaveReq.getStudentId())
                        .build());

        if (courseChapterReviewDescribe != null) {
            ICourseChapterReviewCountDto iCourseChapterReviewCountDto = courseChapterReviewDescribeRepository.averageScoreAndSum(reviewSaveReq.getChapterId());
            courseChapterReviewRepository.save(CourseChapterReviewBuilder
                    .aCourseChapterReview()
                    .withAverageScore(iCourseChapterReviewCountDto.getAverageScore() != null ? NumberUtil.roundStr(iCourseChapterReviewCountDto.getAverageScore(), 1) : "0")
                    .withChapterId(reviewSaveReq.getChapterId())
                    .withUpdateUser(reviewSaveReq.getStudentId())
                    .withCreateUser(reviewSaveReq.getStudentId())
                    .withReviewAmount(iCourseChapterReviewCountDto.getReviewAmount())
                    .build()
            );
            return "评价成功!";
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "保存失败");
        return "";
    }

    @Override
    public CourseChapterReviewResp findChapterReview(String chapterId) {
        Optional<CourseChapterReview> optional = courseChapterReviewRepository.findByIsValidatedEqualsAndChapterId(TAKE_EFFECT_OPEN, chapterId);
        if (optional.isPresent()) {
            CourseChapterReview courseChapterReview = optional.get();
            return new CourseChapterReviewResp(courseChapterReview.getChapterId(), courseChapterReview.getAverageScore(), courseChapterReview.getReviewAmount());
        }
        return new CourseChapterReviewResp();
    }

//    @Override
//    public List<IStudentDto> findCourseChapterStudentsAll(String chapterId) {
//        return courseChapterReviewDescribeRepository.findCourseChapterReviewByChapterId(chapterId);
//    }

//    @Override
//    public CourseChapterReviewDescribeResp findMyCourseChapterReview(String studentId, String chapterId) {
//        CourseChapterReviewDescribe courseChapterReviewDescribe = courseChapterReviewDescribeRepository.findByIsValidatedEqualsAndStudentIdAndChapterId(TAKE_EFFECT_OPEN, studentId, chapterId);
//        return CourseChapterReviewDescribeResp.builder()
//                .chapterId(courseChapterReviewDescribe.getChapterId())
//                .score(courseChapterReviewDescribe.getScore())
//                .studentId(courseChapterReviewDescribe.getStudentId())
//                .build();
//    }

}
