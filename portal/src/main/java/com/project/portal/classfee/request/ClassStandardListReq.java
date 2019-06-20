package com.project.portal.classfee.request;

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
@ApiModel(value = "课程标准查询")
public class ClassStandardListReq implements Serializable {

    @ApiModelProperty(name = "centerAreaId", value = "学习中心", dataType = "string", required = true)
    private String centerAreaId;

    @ApiModelProperty(value = "所属年份", name = "createYear", dataType = "string")
    private String createYear;

    @Builder.Default
    @ApiModelProperty(value = "分页排序字段", name = "sortVo")
    private SortVo sortVo = new SortVo();

}
