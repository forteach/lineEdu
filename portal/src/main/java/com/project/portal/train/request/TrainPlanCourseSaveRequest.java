package com.project.portal.train.request;

import com.project.portal.request.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description: 培训计划课程
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "培训计划课程添加修改")
public class TrainPlanCourseSaveRequest extends BaseReq {
    /**
     * 项目id
     */
    @ApiModelProperty(name = "trainProjectId", value = "项目计划id")
    private String pjPlanId;

    @ApiModelProperty(name = "courseId", value = "课程编号")
    private String courseId;

    /**
     * 项目名称
     */
    @ApiModelProperty(name = "courseName", value = "课程名称")
    private String courseName;

    /**
     * 项目领域
     */
    @ApiModelProperty(name = "trainAreaId", value = "项目领域")
    private String trainAreaId;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}