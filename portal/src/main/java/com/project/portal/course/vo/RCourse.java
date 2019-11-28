package com.project.portal.course.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 科目
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 16:42
 */
@Data
@ApiModel(value = "科目课程")
public class RCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科目编号ID", name = "courseId", dataType = "string", example = "ff808181673e8df401673e8e49cb0000")
    private String courseId;

    @ApiModelProperty(name = "courseName", value = "科目名称", dataType = "string", example = "商务英语", required = true)
    private String courseName;

    @ApiModelProperty(name = "courseNumber", value = "课程编号", dataType = "string", example = "S123456", required = true)
    private String courseNumber;

    @ApiModelProperty(value = "封面图片路径", name = "topPicSrc", notes = "保存的是封面图片路径", example = "http://wx2.sinaimg.cn/large/006nLajtly1fk65lrevkqj30dw0dwadz.jpg")
    private String topPicSrc;

    @ApiModelProperty(name = "courseDescribe", value = "课程描述富文本", notes = "长字段富文本", example = "<p>富文本</p>")
    private String courseDescribe;

    @ApiModelProperty(name = "alias", value = "别名", dataType = "string", example = "第一学期")
    private String alias;

    @ApiModelProperty(name = "isRequired", value = "是否必修课 Y/N", dataType = "string")
    private String isRequired;

    @ApiModelProperty(name = "learningTime", value = "需要学习的总时长(小时)", dataType = "string")
    private String learningTime;

    @ApiModelProperty(name = "videoPercentage", value = "观看视频占百分比", dataType = "string")
    private String videoPercentage;

    @ApiModelProperty(name = "jobsPercentage", value = "平时作业占百分比", dataType = "string")
    private String jobsPercentage;

    @ApiModelProperty(name = "createUser", value = "创建人", hidden = true)
    private String createUser;
}