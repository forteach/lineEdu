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
@ApiModel(value = "培训 分页查询班级对应的资料信息")
@EqualsAndHashCode(callSuper = true)
public class ClassFileFindAllPage extends SortVo implements Serializable {

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;

    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string")
    private String classId;
}