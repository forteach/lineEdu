package com.project.portal.train.request;

import com.project.portal.train.vo.TrainPlanCourseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 22:26
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "培训计划课程修改保存对象")
public class TrainPlanCourseSaveUpateRequest implements Serializable {

    @ApiModelProperty(name = "list", value = "培训计划集合")
    private List<TrainPlanCourseVo> list;

    @ApiModelProperty(name = "planId", value = "培训项目计划编号", dataType = "string")
    private String planId;
}