package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 16:24
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "保存修改学生扩展信息")
public class StudentExpandRequest implements Serializable {
    /**
     * 补充编号/扩展编号
     */
    @ApiModelProperty(name = "expandId", value = "扩展编号", dataType = "string")
    private String expandId;

    /**
     * 补充字段名称
     */
    @ApiModelProperty(name = "expandName", value = "补充字段名称", dataType = "string")
    private String expandName;

    /**
     * 补充字段值
     */
    @ApiModelProperty(name = "expandValue", value = "补充字段值", dataType = "string")
    private String expandValue;
}
