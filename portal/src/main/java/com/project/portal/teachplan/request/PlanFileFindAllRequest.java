package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 10:07
 * @version: 1.0
 * @description:
 */
@Data
public class PlanFileFindAllRequest implements Serializable {
    @ApiModelProperty(name = "planId", value = "计划id", dataType = "string", required = true)
    private String planId;
    @ApiModelProperty(name = "classId", value = "班级Id", dataType = "string", required = true)
    private String classId;
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string", required = true)
    private String courseId;
    @ApiModelProperty(name = "createDate", value = "创建时间", dataType = "string", required = true)
    private String createDate;
}
