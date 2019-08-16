package com.project.portal.train.request;

import com.project.portal.request.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description: 培训项目班级
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "培训项目班级添加修改")
public class TrainClassSaveRequest extends BaseReq {
    /**
     * 项目id
     */
    @ApiModelProperty(name = "trainProjectId", value = "项目计划id")
    private String pjPlanId;

    @ApiModelProperty(name = "trainProjectId", value = "培训项目班级编号")
    private String trainClassId;


    @ApiModelProperty(name = "trainClassName", value = "培训班级名称")
    private String trainClassName;


    @ApiModelProperty(name = "classAdmin", value = "培训班级管理员")
    private String classAdmin;


    @ApiModelProperty(name = "classAdminTel", value = "培训班级管理员电话")
    private String classAdminTel;


    @ApiModelProperty(name = "lineOnLine", value = "线上线下")
    private String lineOnLine;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}
