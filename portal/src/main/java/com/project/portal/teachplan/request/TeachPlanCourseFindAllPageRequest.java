package com.project.portal.teachplan.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/11/5 14:06
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeachPlanCourseFindAllPageRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "planId", value = "计划Id", dataType = "string", required = true)
    private String planId;
}
