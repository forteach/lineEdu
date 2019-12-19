package com.project.portal.user.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-26 16:38
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherFindAllPageRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "verifyStatus", value = "审核状态 0 已经审核, 1 没有审核 2 拒绝", dataType = "string")
    private String verifyStatus;
    @ApiModelProperty(name = "teacherName", value = "教师姓名", dataType = "string")
    private String teacherName;
    @ApiModelProperty(name = "centerAreaId", value = "学习中心id", dataType = "string")
    public String centerAreaId;
    @ApiModelProperty(name = "phone", value = "电话", dataType = "string")
    public String phone;
}
