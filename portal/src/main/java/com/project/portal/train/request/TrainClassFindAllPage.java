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
@ApiModel(value = "培训分页查询班级计划")
public class TrainClassFindAllPage extends SortVo implements Serializable {

    @ApiModelProperty(name = "planId", value = "培训项目计划编号", dataType = "string", required = true)
    private String planId;
}
