package com.project.portal.course.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-8 09:42
 * @version: 1.0
 * @description:
 */
@Data
public class CourseImageFindReq implements Serializable {
    @ApiModelProperty(name = "courseId", value = "课程id")
    private String courseId;
}
