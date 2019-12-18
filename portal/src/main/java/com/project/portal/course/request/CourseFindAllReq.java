package com.project.portal.course.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.print.attribute.standard.MediaSize;
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
@ApiModel(value = "课程所有列表")
@EqualsAndHashCode(callSuper = true)
public class CourseFindAllReq extends SortVo implements Serializable {

    @ApiModelProperty(value = "用户编号", name = "userId", dataType = "string")
    private String userId;
    @ApiModelProperty(name = "courseType", value = "课程类型 1 线上 2，线下 3 混合", dataType = "int")
    private String courseType;
}
