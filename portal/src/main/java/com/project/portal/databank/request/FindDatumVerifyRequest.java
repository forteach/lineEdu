package com.project.portal.databank.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 11:39
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "查询需要审核的课程信息分页对象")
public class FindDatumVerifyRequest extends SortVo implements Serializable {

//    @ApiModelProperty(name = "courseName", value = "课程名称", dataType = "string")
//    private String courseName;

    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string")
    private String courseId;
}
