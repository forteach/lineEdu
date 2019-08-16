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
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "培训项目计划添加修改")
public class TrainProjectPlanSaveRequest extends BaseReq {
    /**
     * 项目id
     */
    @ApiModelProperty(name = "pjPlanId", value = "项目计划id", dataType = "string")
    private String pjPlanId;

    /**
     * 项目领域
     */
    @ApiModelProperty(name = "trainAreaId", value = "项目领域", dataType = "string")
    private String trainAreaId;

    /**
     * 项目名称
     */
    @ApiModelProperty(name = "applyStart", value = "报名开始时间", dataType = "string")
    private String applyStart;

    @ApiModelProperty(name = "applyEnd", value = "报名结束时间", dataType = "string")
    private String applyEnd;

    @ApiModelProperty(name = "trainStart", value = "计划开始执行时间", dataType = "string")
    private String trainStart;

    @ApiModelProperty(name = "trainEnd", value = "计划执行结束时间", dataType = "string")
    private String trainEnd;

    @ApiModelProperty(name = "trainAdmin", value = "计划负责人", dataType = "string")
    private String trainAdmin;

    @ApiModelProperty(name = "trainProjectId", value = "培训项目编号", dataType = "string")
    private String trainProjectId;

    @ApiModelProperty(name = "trainProjectName", value = "培训项目名称", dataType = "string")
    private String trainProjectName;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}