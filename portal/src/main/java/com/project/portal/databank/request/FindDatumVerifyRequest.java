package com.project.portal.databank.request;

import com.project.portal.request.SortVo;
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
public class FindDatumVerifyRequest extends SortVo implements Serializable {
//    @ApiModelProperty(name = "verify", value = "审核状态 0 已经审核, 1 没有审核 2 拒绝")
//    private String verify;
    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string")
    private String courseId;
}
