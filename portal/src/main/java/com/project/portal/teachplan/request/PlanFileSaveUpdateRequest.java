package com.project.portal.teachplan.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/16 00:19
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "在线计划班级资料信息保存修改")
public class PlanFileSaveUpdateRequest implements Serializable {

    private String fileId;

    @ApiModelProperty(name = "fileName", value = "资料名称", dataType = "string")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "资料URL", dataType = "string")
    private String fileUrl;

    @ApiModelProperty(name = "classId", value = "班级编号", dataType = "string")
    private String classId;

    @ApiModelProperty(name = "fileType", value = "资料类型", dataType = "string")
    private String fileType;

    /**
     * 项目id
     */
    @ApiModelProperty(name = "planId", value = "项目计划id", dataType = "string")
    private String planId;

    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string")
    private String courseId;

    @ApiModelProperty(name = "type", value = "资料类型，上传分类 1.签到 2.教材、3.日志、4.照片", dataType = "string")
    private String type;

    @ApiModelProperty(name = "createDate", value = "上课日期", dataType = "string")
    private String createDate;
}