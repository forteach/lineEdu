package com.project.portal.course.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class CourseStatusReq implements Serializable {
    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string", required = true)
    private String courseId;
    @ApiModelProperty(name = "userId", value = "用户ID", dataType = "string", required = true)
    private String userId;
}
