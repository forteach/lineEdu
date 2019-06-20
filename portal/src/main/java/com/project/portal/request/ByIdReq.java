package com.project.portal.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:　表的基本信息
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/30 15:53
 */

@Data
public  class ByIdReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", name = "id",  dataType = "string")
    private String id ;


}
