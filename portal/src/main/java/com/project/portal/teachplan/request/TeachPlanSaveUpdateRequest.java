package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeachPlanSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "planId", dataType = "string", value = "计划id")
    private String planId;

    @ApiModelProperty(name = "planName", dataType = "string", value = "计划名称")
    private String planName;

//    @ApiModelProperty(name = "courseIds", dataType = "list", value = "课程id集合")
//    private List<String> courseIds;

//    @ApiModelProperty(name = "classIds", dataType = "list", value = "班级id集合")
//    private List<String> classIds;

    @ApiModelProperty(name = "planAdmin", dataType = "string", value = "计划负责人")
    private String planAdmin;

    @ApiModelProperty(name = "startDate", dataType = "string", value = "计划结束时间")
    private String startDate;

    @ApiModelProperty(name = "endDate", dataType = "string", value = "计划结束时间")
    private String endDate;

    @ApiModelProperty(name = "teacherId", dataType = "string", value = "创建的教师id")
    private String teacherId;

    @ApiModelProperty(name = "centerAreaId", dataType = "string", hidden = true)
    private String centerAreaId;
}