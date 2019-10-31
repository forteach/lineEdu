package com.project.portal.course.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-31 15:15
 * @version: 1.0
 * @description:
 */
@Data
public class CourseChapterRecordFindReq implements Serializable {
    /** 学生id*/
    @ApiModelProperty(name = "studentId", value = "学生id", dataType = "string")
    private String studentId;
    /** 课程id*/
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string")
    private String courseId;
    /** 章节id*/
    @ApiModelProperty(name = "chapterId", value = "章节Id", dataType = "string")
    private String chapterId;
}
