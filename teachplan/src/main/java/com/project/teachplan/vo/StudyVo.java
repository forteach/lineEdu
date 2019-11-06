package com.project.teachplan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-6 12:06
 * @version: 1.0
 * @description:
 */
@Data
public class StudyVo implements Serializable {
    private String courseId;

    private Integer onLineTime;

    private Integer onLineTimeSum;

    private Integer answerSum;

    private Integer correctSum;

    public StudyVo() {
    }

    public StudyVo(String courseId, Integer onLineTime, Integer onLineTimeSum, Integer answerSum, Integer correctSum) {
        this.courseId = courseId;
        this.onLineTime = onLineTime;
        this.onLineTimeSum = onLineTimeSum;
        this.answerSum = answerSum;
        this.correctSum = correctSum;
    }
}
