package com.project.portal.schoolroll.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 10:48
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "学生成绩信息")
public class StudentScoreRequest extends SortVo implements Serializable {

    @ApiModelProperty(name = "studentId", value = "学生Id", dataType = "string", required = true)
    private String studentId;

    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string")
    private String courseId;

//    @ApiModelProperty(name = "term", value = "学期", dataType = "string")
//    private String term;

    @ApiModelProperty(name = "courseType", value = "课程类别", dataType = "string")
    private String type;

//    @ApiModelProperty(name = "schoolYear", value = "学年", dataType = "string")
//    private String schoolYear;

    @ApiModelProperty(name = "startDate", value = "开始日期", dataType = "string")
    private String startDate;

    @ApiModelProperty(name = "endDate", value = "结束日期", dataType = "string")
    private String endDate;
}