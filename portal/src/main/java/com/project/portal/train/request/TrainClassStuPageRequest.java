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
 * @Date: 2019/8/15 23:14
 * @Version: 1.0
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "查询班级学生分页")
public class TrainClassStuPageRequest extends SortVo implements Serializable {

    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string")
    private String classId;
}