package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import com.project.teachplan.vo.TeachPlanCourseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeachPlanSaveUpdateRequest extends BaseTeachPlanSaveUpdateRequest {

//    @ApiModelProperty(name = "planId", dataType = "string", value = "计划id")
//    private String planId;

//    @ApiModelProperty(name = "planName", dataType = "string", value = "计划名称")
//    private String planName;

    @ApiModelProperty(name = "planAdmin", dataType = "string", value = "计划负责人")
    private String planAdmin;

    @ApiModelProperty(name = "startDate", dataType = "string", value = "计划结束时间")
    private String startDate;

    @ApiModelProperty(name = "endDate", dataType = "string", value = "计划结束时间")
    private String endDate;

    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string", required = true)
    private String classId;

    @ApiModelProperty(name = "remark", value = "备注说明", dataType = "string")
    private String remark;

    @ApiModelProperty(name = "courses", dataType = "list", value = "课程集合")
    private List<TeachPlanCourseVo> courses;

//    @ApiModelProperty(name = "specialtyName", value = "专业名称", dataType = "string")
//    private String specialtyName;
//    @ApiModelProperty(name = "grade", value = "年级", dataType = "string")
//    private String grade;
//    @ApiModelProperty(name = "className", value = "班级名称", dataType = "string", required = true)
//    private String className;
}