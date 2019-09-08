package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import com.project.teachplan.vo.TeachPlanCourseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 12:37
 * @Version: 1.0
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeachPlanCourseSaveUpdateRequest extends BaseTeachPlanSaveUpdateRequest {
    @ApiModelProperty(name = "courses", dataType = "list", value = "课程集合")
    private List<TeachPlanCourseVo> courses;
    @ApiModelProperty(name = "teacherId", dataType = "string", value = "创建的教师id")
    private String teacherId;
}
