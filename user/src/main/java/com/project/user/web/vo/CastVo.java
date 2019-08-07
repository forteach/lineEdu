package com.project.user.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/1 3:09
 */
@Data
public class CastVo implements Serializable {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 用户ID集合
     */
    private List<String> userIds;
}
