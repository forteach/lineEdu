package com.project.portal.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-10 11:59
 * @version: 1.0
 * @description:
 */
@Data
public class TeacherUploadFileRequest implements Serializable {
    @ApiModelProperty(name = "teacherId", value = "教师id", dataType = "string")
    private String teacherId;
    @ApiModelProperty(name = "fileUrl", value = "文件路径", dataType = "string", required = true)
    private String fileUrl;
    @ApiModelProperty(name = "fileName", value = "文件名称", dataType = "string")
    private String fileName;
    @ApiModelProperty(name = "type", value = "文件资料类型: A.身份信息,B.学历信息,C.职称信息,D.其它")
    private String type;
}
