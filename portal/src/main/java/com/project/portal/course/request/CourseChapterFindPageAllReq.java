package com.project.portal.course.request;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 12:43
 * @version: 1.0
 * @description:
 */

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourseChapterFindPageAllReq extends SortVo implements Serializable {
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string")
    private String courseId;
    @ApiModelProperty(name = "studentId", value = "学生id", dataType = "string")
    private String studentId;
}
