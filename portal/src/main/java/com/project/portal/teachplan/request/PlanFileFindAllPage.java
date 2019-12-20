package com.project.portal.teachplan.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 23:44
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "分页查询班级对应的资料信息")
@EqualsAndHashCode(callSuper = true)
public class PlanFileFindAllPage extends SortVo implements Serializable {

//    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string", required = true)
//    private String classId;

    @ApiModelProperty(name = "planId", value = "计划Id", dataType = "string", required = true)
    private String planId;

    @ApiModelProperty(name = "createDate", value = "创建日期", dataType = "string")
    private String createDate;
}