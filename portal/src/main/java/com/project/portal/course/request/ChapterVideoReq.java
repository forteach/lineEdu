package com.project.portal.course.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-31 15:34
 * @version: 1.0
 * @description:
 */
@Data
public class ChapterVideoReq implements Serializable {
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string")
    private String courseId;

    @ApiModelProperty(name = "courseName", value = "课程名称", dataType = "string")
    private String courseName;
    /** 章节id*/
    @ApiModelProperty(name = "chapterId", value = "章节Id", dataType = "string")
    private String chapterId;
}
