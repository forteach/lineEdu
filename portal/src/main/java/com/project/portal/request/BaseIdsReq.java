package com.project.portal.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-24 10:24
 * @version: 1.0
 * @description:
 */
@Data
public class BaseIdsReq implements Serializable {

    @ApiModelProperty(name = "ids", value = "id集合数组", dataType = "list")
    private List<String> ids;
}
