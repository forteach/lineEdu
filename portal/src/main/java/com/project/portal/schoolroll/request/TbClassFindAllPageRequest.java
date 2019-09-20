package com.project.portal.schoolroll.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 10:31
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TbClassFindAllPageRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "centerAreaId", value = "学习中心id", dataType = "string")
    private String centerAreaId;
}
