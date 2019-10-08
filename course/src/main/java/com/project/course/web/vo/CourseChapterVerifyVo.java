package com.project.course.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-8 09:52
 * @version: 1.0
 * @description:
 */
@Data
public class CourseChapterVerifyVo implements Serializable {
    private String chapterId;
    private String verifyStatus;
    private String remark;
    private String userId;

    public CourseChapterVerifyVo() {
    }

    public CourseChapterVerifyVo(String chapterId, String verifyStatus, String remark, String userId) {
        this.chapterId = chapterId;
        this.verifyStatus = verifyStatus;
        this.remark = remark;
        this.userId = userId;
    }
}
