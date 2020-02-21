package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2019/12/30 11:53
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreatePlanFileRequest extends BaseTeachPlanSaveUpdateRequest {
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string", required = true)
    private String courseId;
    @ApiModelProperty(name = "createDate", value = "创建日期", dataType = "string", required = true)
    private String createDate;
    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string", required = true)
    private String classId;
}