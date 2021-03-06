package com.project.portal.information.request.notice;


import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-29 16:21
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "中心公告的倒序列表")
public class FindCerterIdListRequest extends SortVo implements Serializable {

    @ApiModelProperty(value = "centerId", name = "centerId")
    private String centerId;
}
