package com.project.portal.train.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/15 23:01
 * @Version: 1.0
 * @Description:
 */
@Data
public class TrainCourseSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "courseId", value = "培训课程编号", dataType = "string")
    private String courseId;

    @ApiModelProperty(name = "courseName", value = "培训课程名称", dataType = "string")
    private String courseName;

    @ApiModelProperty(name = "trainAreaId", value = "培训项目领域", dataType = "string")
    private String trainAreaId;
}