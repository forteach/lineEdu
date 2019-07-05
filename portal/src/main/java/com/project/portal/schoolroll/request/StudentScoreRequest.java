package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:48
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "学生成绩信息")
public class StudentScoreRequest implements Serializable {

    @ApiModelProperty(name = "stuId", value = "学生id", dataType = "string", required = true)
    private String stuId;

    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string")
    private String courseId;

}
