package com.project.mongodb.domain;

import cn.hutool.core.util.StrUtil;
import com.project.mongodb.domain.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-22 10:16
 * @version: 1.0
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecord extends BaseEntity  {
    /** 用户ID*/
    @Indexed
    private String userId;
    /** 用户名*/
    private String userName;
    /** 学习中心Id*/
    @Indexed
    private String centerAreaId;
    /** 学习名称*/
    private String centerName;
    /** 描述说明*/
    private String description;
    /** 操作 添加 删除 修改 */
    private String operating;
    /** 操作结果 ok / fail */
    private String result = StrUtil.isBlank(this.result) ? "ok" : this.result;
    /** 修改数据*/
    private Object data;

    public UserRecord(String userId, String userName, String centerAreaId, String centerName,
                      String description, String operating, Object data) {
        this.userId = userId;
        this.userName = userName;
        this.centerAreaId = centerAreaId;
        this.centerName = centerName;
        this.description = description;
        this.operating = operating;
        this.data = data;
    }
}