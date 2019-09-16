package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlanFileFindAllRequest extends BaseTeachPlanSaveUpdateRequest {
    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string")
    private String classId;
}
