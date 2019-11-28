package com.project.portal.course.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-11 16:58
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改课程章节的对象")
public class CourseChapterEditReq implements Serializable {

    @ApiModelProperty(value = "章节编号", name = "chapterId", dataType = "string", required = true)
    private String chapterId;

    @ApiModelProperty(value = "章节名称", name = "chapter_name", dataType = "string")
    private String chapterName;

    @ApiModelProperty(value = "是否发布", name = "publish", dataType = "string", notes = "是否发布　Y(是) N(否)", example = "Y")
    private String publish;

    @ApiModelProperty(name = "randomQuestionsNumber", value = "随机题目数量", dataType = "int")
    private Integer randomQuestionsNumber;

    @ApiModelProperty(name = "videoTime", value = "需要观看视频长度(秒)", dataType = "int")
    private Integer videoTime;
}