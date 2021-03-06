package com.project.portal.course.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 23:01
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "在线课程字典保存修改对象")
public class OnLineCourseDicSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "courseId", value = "培训课程编号", dataType = "string")
    private String courseId;

    @ApiModelProperty(name = "courseName", value = "培训课程名称", dataType = "string")
    private String courseName;

    @ApiModelProperty(name = "type", value = "课程类型　1.线上，2.线下,3.混合", dataType = "string")
    private String type;
}