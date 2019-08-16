package com.project.portal.train.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 23:49
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "培训班级保存修改")
public class TrainClassSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "trainClassId", value = "培训项目班级编号", dataType = "string")
    private String trainClassId;

    @ApiModelProperty(name = "trainClassName", value = "培训班级名称", dataType = "string")
    private String trainClassName;

    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号", dataType = "string")
    private String pjPlanId;

    @ApiModelProperty(name = "classAdmin", value = "培训班级管理员", dataType = "string")
    private String classAdmin;

    @ApiModelProperty(name = "classAdminTel", value = "培训班级管理员电话", dataType = "string")
    private String classAdminTel;

    @ApiModelProperty(name = "lineOnLine", value = "线上线下", dataType = "string")
    private String lineOnLine;
}