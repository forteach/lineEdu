package com.project.portal.train.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 22:36
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "培训计划课程管理")
public class TrainPlanCourseVo implements Serializable {

    @ApiModelProperty(name = "courseId", value = "培训课程编号", dataType = "string")
    private String courseId;

    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号", dataType = "string")
    private String pjPlanId;

    @ApiModelProperty(name = "courseName", value = "培训课程名称", dataType = "string")
    private String courseName;

    @ApiModelProperty(name = "teacherName", value = "培训课程教师名称", dataType = "string")
    private String teacherName;

    @ApiModelProperty(name = "credit", value = "培训课程学分", dataType = "string")
    private String credit;
}