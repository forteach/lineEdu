package com.project.portal.schoolroll.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 17:00
 * @Version: 1.0
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentOnLineFindAllPageRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "studentName", value = "学生姓名", dataType = "string")
    public String studentName;
    @ApiModelProperty(name = "isValidated", value = "学生状态 0生效 1失效", dataType = "string")
    public String isValidated;
}
