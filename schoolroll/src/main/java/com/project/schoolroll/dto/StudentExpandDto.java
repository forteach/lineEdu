package com.project.schoolroll.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 15:17
 * @version: 1.0
 * @description:
 */
public interface StudentExpandDto {
    /**
     * 补充编号/扩展编号
     * @return
     */
    public String getExpandId();
    /**
     * 补充字段名称
     * @return
     */
    public String getExpandName();

    /**
     * 补充字段值
     * @return
     */
    public String getExpandValue();
}
