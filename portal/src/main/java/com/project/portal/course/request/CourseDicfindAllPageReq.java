package com.project.portal.course.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2019/12/17 10:57
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseDicfindAllPageReq extends SortVo implements Serializable {
    @ApiModelProperty(name = "type", value = "课程类型　1.线上，2.线下,3.混合", dataType = "string")
    private String type;
    @ApiModelProperty(name = "courseName", value = "课程名称", dataType = "string")
    private String courseName;
}
