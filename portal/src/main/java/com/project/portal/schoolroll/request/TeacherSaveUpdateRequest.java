package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-9 11:26
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "教师保存修改信息对象")
public class TeacherSaveUpdateRequest implements Serializable {
    @ApiModelProperty(name = "teacherId", value = "教师id", dataType = "string")
    private String teacherId;

    @ApiModelProperty(name = "teacherName", value = "教师名称", dataType = "string")
    private String teacherName;

    @ApiModelProperty(name = "teacherCode", value = "教师代码", dataType = "string")
    private String teacherCode;
}