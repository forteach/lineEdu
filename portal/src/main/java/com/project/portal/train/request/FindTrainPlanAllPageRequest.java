package com.project.portal.train.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 21:56
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "培训中心分页查询归属中心计划")
public class FindTrainPlanAllPageRequest extends SortVo implements Serializable {

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;

    @ApiModelProperty(name = "agoDay", value = "获取前多少天项目计划列表 前多少天", dataType = "int")
    private String agoDay;
}
