package com.project.portal.course.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-22 09:49
 * @Version: 1.0
 * @Description:
 */
@Data
@ApiModel(value = "课程添加成功", description = "课程信息")
public class CourseListResp implements Serializable {

    @ApiModelProperty(value = "课程id", name = "courseId", dataType = "string")
    private String courseId;

    @ApiModelProperty(value = "别名", name = "别名", dataType = "string")
    private String alias;

    @ApiModelProperty(name = "courseName", value = "课程名称", dataType = "string")
    private String courseName;

    @ApiModelProperty(value = "封面图片路径", name = "topPicSrc", dataType = "string")
    private String topPicSrc;

    @ApiModelProperty(value = "课程描述", name = "courseDescribe", dataType = "string")
    private String courseDescribe;

    @ApiModelProperty(value = "章节id", name = "chapterId", dataType = "string")
    private String chapterId;

    @ApiModelProperty(value = "章节名称", name = "chapterName", dataType = "string")
    private String chapterName;

    @ApiModelProperty(value = "已经上课的课程id", name = "joinChapterId", dataType = "string")
    private String joinChapterId;

    @ApiModelProperty(value = "已经上课的课程名称", name = "joinChapterName", dataType = "string")
    private String joinChapterName;
    @ApiModelProperty(name = "isValidated", value = "生效标识 0生效 1失效生效标识 0生效 1失效")
    private String isValidated;
    @ApiModelProperty(name = "createTime", value = "创建时间 yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @ApiModelProperty(name = "courseType", value = "课程类型 1 线上 2，线下 3 混合", dataType = "int")
    private Integer courseType;

    public CourseListResp(String courseId, String courseName, String topPicSrc, String alias, String isValidated, String createTime, Integer courseType) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.topPicSrc = topPicSrc;
        this.alias = alias;
        this.isValidated = isValidated;
        this.createTime = createTime;
        this.courseType = courseType;
    }

    public CourseListResp() {
    }
}
