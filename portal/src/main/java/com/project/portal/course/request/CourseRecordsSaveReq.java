package com.project.portal.course.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-5 10:55
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "学习视频时长保存")
public class CourseRecordsSaveReq implements Serializable {
    /** 学生id*/
    @ApiModelProperty(name = "studentId", value = "学生id", dataType = "string")
    private String studentId;
    /** 课程id*/
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string")
    private String courseId;
    /** 章节id*/
    @ApiModelProperty(name = "chapterId", value = "章节Id", dataType = "string")
    private String chapterId;
    /** 观看视频位置*/
    @ApiModelProperty(name = "locationTime", value = "观看视频位置", dataType = "string")
    private String locationTime;
    /** 观看视频时间长度*/
    @ApiModelProperty(name = "duration", value = "观看视频时间长度", dataType = "int")
    private Integer duration;
    /** 视频总长度*/
    @ApiModelProperty(name = "videoDuration", value = "视频总长度(秒)", dataType = "int")
    private Integer videoDuration;
}
