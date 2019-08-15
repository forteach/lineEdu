package com.project.portal.train.request;

import com.project.portal.request.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "培训项目计划添加修改")
public class TrainProjectPlanSaveRequest extends BaseReq {
    /**
     * 项目id
     */
    @ApiModelProperty(name = "trainProjectId", value = "项目计划id")
    private String pjPlanId;

    /**
     * 项目领域
     */
    @ApiModelProperty(name = "trainAreaId", value = "项目领域")
    private String trainAreaId;

    /**
     * 项目名称
     */
    @ApiModelProperty(name = "trainProjectName", value = "报名开始时间")
    private String applyStart;

    @ApiModelProperty(name = "trainProjectName", value = "报名结束时间")
    private String applyEnd;

    @ApiModelProperty(name = "trainProjectName", value = "计划开始执行时间")
    private String trainStrat;

    @ApiModelProperty(name = "trainProjectName", value = "计划执行结束时间")
    private String trainEnd;

    @ApiModelProperty(name = "trainProjectName", value = "计划负责人")
    private String trainAdmin;

}
