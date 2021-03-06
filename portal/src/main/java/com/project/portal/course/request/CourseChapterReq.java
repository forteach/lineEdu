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
 * @Date: 18-11-16 14:09
 * @Version: 1.0
 * @Description: 科目章节
 */
@Data
@Builder
@ApiModel(value = "科目章节")
@AllArgsConstructor
@NoArgsConstructor
public class CourseChapterReq implements Serializable {

    @ApiModelProperty(name = "科目编号", value = "courseId", dataType = "string", required = true)
    private String courseId;

    @ApiModelProperty(value = "章节编号", name = "chapterId", dataType = "string", hidden = true)
    private String chapterId;

    @ApiModelProperty(value = "章节名称", name = "chapter_name", dataType = "string", required = true)
    private String chapterName;

    @ApiModelProperty(value = "章节父编号", name = "chapterParentId", dataType = "string")
    private String chapterParentId;

    @ApiModelProperty(value = "层级位置", name = "sort", dataType = "String", notes = "树型结构所处的顺序默认１")
    private String sort = "1";

    @ApiModelProperty(name = "chapterType", value = "目录类型", notes = "目录类型：1.章、２.节、3.小节", dataType = "string", required = true)
    private String chapterType;

    @ApiModelProperty(name = "chapterType", value = "层级")
    private String chapterLevel;

    @ApiModelProperty(value = "是否发布", name = "publish", dataType = "string", notes = "是否发布　Y(是) N(否)", example = "Y")
    private String publish = "Y";

    @ApiModelProperty(name = "randomQuestionsNumber", value = "随机题目数量", dataType = "int")
    private Integer randomQuestionsNumber;

    @ApiModelProperty(name = "videoTime", value = "需要观看视频长度(秒)", dataType = "int")
    private Integer videoTime;
}