package com.project.schoolroll.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 15:59
 * @version: 1.0
 * @description:
 */
@Data
public class StudentExpandVo implements Serializable {

    /**
     * 补充编号/扩展编号
     */
    private String expandId;

    /**
     * 补充字段名称
     */
    private String expandName;

    /**
     * 补充字段值
     */
    private String expandValue;
}
