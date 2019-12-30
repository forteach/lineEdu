package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 10:07
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanFileFindAllRequest extends BaseTeachPlanSaveUpdateRequest {
//    @ApiModelProperty(name = "planId", value = "计划id", dataType = "string", required = true)
//    private String planId;
    @ApiModelProperty(name = "classId", value = "班级Id", dataType = "string", required = true)
    private String classId;
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string", required = true)
    private String courseId;
    @ApiModelProperty(name = "createDate", value = "创建时间", dataType = "string", required = true)
    private String createDate;
    @ApiModelProperty(name = "verifyStatus", value = "计划状态 0 同意,1 已经提交,2 不同意拒绝", dataType = "string")
    private String verifyStatus;
}