package com.project.teachplan.vo;

import lombok.Data;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 15:44
 * @version: 1.0
 * @description:
 */
@Data
public class ScoreVo {
    private String courseId;

    private String courseName;

    private String score;

    public ScoreVo(String courseId, String courseName, String score) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
    }
}
