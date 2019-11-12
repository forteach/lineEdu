package com.project.portal.course.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-9 16:20
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "分页查询课程评论记录")
public class CourseReviewReq extends SortVo implements Serializable {

    @ApiModelProperty(name = "courseId", value = "课程id", required = true, dataType = "string")
    private String courseId;
}
