package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 12:37
 * @Version: 1.0
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeachPlanCourseSaveUpdateRequest extends BaseTeachPlanSaveUpdateRequest {
    @ApiModelProperty(name = "courseIds", dataType = "list", value = "课程id集合")
    private List<String> courseIds;
}
