package com.project.portal.train.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-26 15:37
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindTrainClassStuAllPageRequest extends SortVo implements Serializable {

    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号", dataType = "string")
    private String pjPlanId;

    @ApiModelProperty(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "string")
    private String agoDay;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}