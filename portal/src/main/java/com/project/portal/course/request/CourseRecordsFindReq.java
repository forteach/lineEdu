package com.project.portal.course.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-1 11:07
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "管理端查询课程学习记录")
public class CourseRecordsFindReq extends SortVo implements Serializable {
    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string")
    private String courseId;

//    @ApiModelProperty(name = "studentId", value = "学生Id", dataType = "string")
    @JsonIgnore
    private String studentId;
}
