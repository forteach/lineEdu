package com.project.user.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/1 2:33
 */
@Data
public class AuthorityVo implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 栏目ID
     */
    private String colId;

    /**
     * 需要保存的栏目动作集合
     */
    private String json;
}