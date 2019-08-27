package com.project.portal.train.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 23:29
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "培训班级课程保存修改对象")
public class TrainClassStuSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "trainStuId", value = "培训班级学生编号", dataType = "string")
    private String trainStuId;

    @ApiModelProperty(name = "trainClassId", value = "培训项目班级编号", dataType = "string")
    private String trainClassId;

    @ApiModelProperty(name = "trainClassName", value = "培训班级名称", dataType = "string")
    private String trainClassName;

    @ApiModelProperty(name = "pjPlanId", value = "培训项目计划编号")
    private String pjPlanId;

    @ApiModelProperty(name = "trainProjectName", value = "培训项目名称", dataType = "string")
    private String trainProjectName;

    @ApiModelProperty(name = "gender", value = "性别", dataType = "string")
    private String gender;

    @ApiModelProperty(name = "stuName", value = "姓名", dataType = "string")
    private String stuName;

    @ApiModelProperty(name = "marriage", value = "民族", dataType = "string")
    private String marriage;

    @ApiModelProperty(name = "nation", value = "民族", dataType = "string")
    private String nation;

    @ApiModelProperty(name = "jobTitle", value = "单位职务", dataType = "string")
    private String jobTitle;

    @ApiModelProperty(name = "stuIdCard", value = "身份证号", dataType = "string")
    private String stuIdCard;

    @ApiModelProperty(name = "stuPhone", value = "联系方式", dataType = "string")
    private String stuPhone;

    @ApiModelProperty(name = "centerAreaId", value = "归属的学习中心编号", dataType = "string")
    private String centerAreaId;
}