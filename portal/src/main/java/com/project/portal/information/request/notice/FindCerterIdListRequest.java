package com.project.portal.information.request.notice;


import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@ApiModel(value = "中心公告的倒序列表")
public class FindCerterIdListRequest implements Serializable {

    @ApiModelProperty(value = "centerId", name = "centerId")
    private String centerId;

    @ApiModelProperty(value = "分页排序字段", name = "sortVo")
    private SortVo sortVo = new SortVo();

}
