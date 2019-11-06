package com.project.portal.course.request;

import com.project.course.web.vo.ChapterDataFileVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-6 14:44
 * @version: 1.0
 * @description:
 */
@Data
public class ChapterDataListReq implements Serializable {
    @ApiModelProperty(name = "courseId", value = "课程Id", dataType = "string", required = true)
    private String courseId;
    @ApiModelProperty(name = "teacherName", value = "教师名称", dataType = "string", required = true)
    private String teacherName;
    @ApiModelProperty(name = "centerName", value = "学习中心名称", dataType = "string", required = true)
    private String centerName;
    @ApiModelProperty(name = "chapterParentId", value = "父章节Id", required = true)
    private String chapterParentId;
    @ApiModelProperty(name = "files", value = "文件信息", dataType = "list", required = true)
    private List<ChapterDataFileVo> files;
}
