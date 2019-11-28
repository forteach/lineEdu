package com.project.portal.course.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-8 10:41
 * @version: 1.0
 * @description:
 */
@Data
public class CourseChapterVerifyReq implements Serializable {
    @ApiModelProperty(name = "chapterId", value = "章节id", required = true, dataType = "string")
    private String chapterId;
    @ApiModelProperty(name = "verifyStatus", value = "审核状态 0 已经审核, 1 没有审核 2 拒绝", required = true, dataType = "string")
    private String verifyStatus;
    @ApiModelProperty(name = "remark", value = "备注", dataType = "string")
    private String remark;
}
