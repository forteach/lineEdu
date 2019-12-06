package com.project.user.web.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-12 15:48
 * @version: 1.0
 * @description:
 */
@Data
public class ActionColumnReq implements Serializable {

    private String colId;

    private String colName;

    private String colNameModel;

    private String colParentId;

    private String colParentName;

    private String colUrl;

    private String colImgUrl;

    private Integer isOrder;
}