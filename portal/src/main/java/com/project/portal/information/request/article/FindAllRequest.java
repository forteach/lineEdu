package com.project.portal.information.request.article;

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
@ApiModel(value = "资讯所有列表")
public class FindAllRequest extends SortVo implements Serializable {

    @ApiModelProperty(value = "资讯分类", name = "articleType")
    private String articleType;

}
