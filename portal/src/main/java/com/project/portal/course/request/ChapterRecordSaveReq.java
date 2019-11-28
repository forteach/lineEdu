package com.project.portal.course.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-10-31 12:11
 * @version: 1.0
 * @description:
 */
@Data
public class ChapterRecordSaveReq implements Serializable {

    /** 学生id*/
    @ApiModelProperty(name = "studentId", value = "学生id", dataType = "string")
    private String studentId;
    /** 课程id*/
    @ApiModelProperty(name = "courseId", value = "课程id", dataType = "string")
    private String courseId;
    /** 章节id*/
    @ApiModelProperty(name = "chapterId", value = "章节Id", dataType = "string")
    private String chapterId;
    /**
     * 视频总长度
     */
    @ApiModelProperty(name = "videoDuration", value = "视频总长度(秒)", dataType = "long")
    private Long videoDuration;
    /** 观看视频位置*/
    @ApiModelProperty(name = "locationTime", value = "观看视频位置", dataType = "string")
    private String locationTime;
    /** 观看视频时间长度*/
    @ApiModelProperty(name = "duration", value = "观看视频时间长度", dataType = "long")
    private Long duration;

    @ApiModelProperty(name = "courseName", value = "课程名称", dataType = "string")
    private String courseName;

    @JsonIgnore
    public String centerAreaId;

    @JsonIgnore
    public String createUser;
}