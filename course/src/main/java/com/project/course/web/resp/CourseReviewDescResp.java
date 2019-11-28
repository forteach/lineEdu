package com.project.course.web.resp;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-9 10:08
 * @version: 1.0
 * @description:
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewDescResp implements Serializable {

    private String studentId;

    private String studentName;

    private String portrait;

    private String reviewId;

    private String reviewDescribe;

    private String classId;

    private String className;

    private Integer score;

    private String createTime;

    private String reply;

    private String replyTime;

    private String teacherId;

    private String teacherName;

}