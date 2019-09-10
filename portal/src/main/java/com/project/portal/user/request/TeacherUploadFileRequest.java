package com.project.portal.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
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
    @ApiModelProperty(name = "file_url", value = "文件路径", dataType = "string")
    private String fileUrl;
}
