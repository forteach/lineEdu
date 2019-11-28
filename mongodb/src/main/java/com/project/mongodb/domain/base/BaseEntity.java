package com.project.mongodb.domain.base;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/15  16:57
 */
@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 传入id为修改  不传id为新增
     */
    @Id
    protected String id;

    /**
     * 更新时间
     */
    protected String uDate = StrUtil.isBlank(this.getUDate()) ? DateUtil.now() : this.getUDate();

}