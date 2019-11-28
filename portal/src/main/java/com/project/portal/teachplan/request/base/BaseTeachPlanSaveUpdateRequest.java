package com.project.portal.teachplan.request.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 12:34
 * @Version: 1.0
 * @Description:
 */
@Data
public class BaseTeachPlanSaveUpdateRequest implements Serializable {
    @ApiModelProperty(name = "planId", value = "培训计划id", dataType = "string", required = true)
    private String planId;
}
