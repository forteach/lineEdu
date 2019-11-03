package com.project.portal.course.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/11/3 22:09
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseStudyFindPage extends SortVo implements Serializable {

    @ApiModelProperty(value = "科目编号ID", name = "courseId", dataType = "string", example = "ff808181673e8df401673e8e49cb0000")
    private String courseId;

    @ApiModelProperty(name = "studentId", value = "学生id", dataType = "string")
    private String studentId;
}