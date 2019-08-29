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
 * @Date: 2019/8/15 23:44
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "培训分页查询财务文件信息")
public class FinanceDetailFileFindAllPage extends SortVo implements Serializable {

    /**
     * 项目id
     */
    @ApiModelProperty(name = "pjPlanId", value = "项目计划id", dataType = "string")
    private String pjPlanId;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}